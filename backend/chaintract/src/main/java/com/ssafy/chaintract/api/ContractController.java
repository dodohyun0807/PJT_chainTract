package com.ssafy.chaintract.api;

import com.ssafy.chaintract.domain.User;
import com.ssafy.chaintract.domain.dto.ContractDto;
import com.ssafy.chaintract.repository.UserRepository;
import com.ssafy.chaintract.service.ContractService;
import io.swagger.annotations.*;
import lombok.Data;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin("*")
@Api("contract 관련 API")
@RestController
@RequestMapping
public class ContractController {
    @Autowired
    ContractService contractService;

    @Autowired
    HttpServletRequest request;

    // TODO: data flow에 유심하여 여러가지 상황에 따라 값을 검증하고 응답값 반환
    @ApiOperation(value = "계약증명 요청 생성", notes = "새 계약증명을 생성해 서명을 받을 수 있음", response = ApiUtils.ApiResult.class)
    @PostMapping("/contract")
    public ApiUtils.ApiResult<?> createContract(@ApiParam(value = "계약증명 정보", required = true) @RequestBody ContractDto contractDto) {
        User user = (User) request.getSession().getAttribute("loginUser");
        contractDto.getParticipantEmails().add(user.getEmail());
        return ApiUtils.success(contractService.createContract(contractDto, user.getEmail()));
    }

    @ApiOperation(value = "블록체인 네트워크에 업로드", notes = "블록체인 네트워크에 암호화된 계약 업로드", response = ApiUtils.ApiResult.class)
    @PutMapping("/contract/{contractId}/block")
    public ApiUtils.ApiResult<?> uploadContractToBlockChain(@ApiParam(value = "계약증명ID", required = true) @PathVariable long contractId) throws Exception {
        contractService.uploadContract(contractId);
        return ApiUtils.success(HttpStatus.SC_OK);
    }

    @ApiOperation(value = "계약서 파일 업로드", notes = "서버에 계약서 파일을 업로드", response = ApiUtils.ApiResult.class)
    @PostMapping("/contract/file")
    public ApiUtils.ApiResult<?> uploadContractFile(@ApiParam(value = "계약서 파일", required = true) @RequestParam List<MultipartFile> files) throws IOException {
        MultipartFile file = files.get(0);
        return ApiUtils.success(contractService.uploadFile(file));
    }

    @ApiOperation(value = "계약서 파일 다운로드", notes = "서버에 계약서 파일을 다운로드", response = ApiUtils.ApiResult.class)
    @GetMapping(value = "/contract/{contractId}/file", produces = "application/pdf")
    public byte[] downloadContractFile(@ApiParam(value = "계약증명ID", required = true) @PathVariable long contractId) throws IOException {
//        return ApiUtils.success(contractService.downloadFile(contractId));
        return contractService.downloadFile(contractId);
    }

    @ApiOperation(value = "계약증명 요청에 서명", notes = "로그인한 이용자가 contractId에 해당하는 증명에 서명", response = ApiUtils.ApiResult.class)
    @PutMapping("/contract/sign/{contractId}")
    public ApiUtils.ApiResult<?> toggleSignature(@ApiParam(value = "계약증명ID", required = true) @PathVariable long contractId) throws Exception {
        User user = (User) request.getSession().getAttribute("loginUser");
        contractService.toggleSignature(contractId, user);
        return ApiUtils.success(HttpStatus.SC_OK);
    }

    // TODO: 성립되지 않은 경우들은 일정 기간 지나면 조회 안되고 삭제되도록
    @ApiOperation(value = "내가 서명하지 않은 계약증명들을 조회", notes = "로그인한 이용자가 서명하지 않은 계약증명들을 반환", response = ApiUtils.ApiResult.class)
    @GetMapping("/contracts/ongoing/need")
    public ApiUtils.ApiResult<?> findContractsNotSigned() {
        User user = (User) request.getSession().getAttribute("loginUser");
        return ApiUtils.success(contractService.getContracts(false, false, false, user));
    }

    @ApiOperation(value = "나는 서명했지만 남이 서명하지 않은 계약증명들을 조회", notes = "로그인한 이용자가 자신은 서명했지만 남은 서명하지 않은 계약증명들을 반환", response = ApiUtils.ApiResult.class)
    @GetMapping("/contracts/ongoing")
    public ApiUtils.ApiResult<?> findUnestablishedContractsSinged(){
        User user = (User) request.getSession().getAttribute("loginUser");
        return ApiUtils.success(contractService.getContracts(false, false, true, user));
    }

    @ApiOperation(value = "성립된 증명들을 조회", notes = "모두가 서명해 성립된 계약증명들을 반환", response = ApiUtils.ApiResult.class)
    @GetMapping("/contracts/complete")
    public ApiUtils.ApiResult<?> findEstablishedContracts(){
        User user = (User) request.getSession().getAttribute("loginUser");
        return ApiUtils.success(contractService.getContracts(false, true, true, user));
    }

    @ApiOperation(value = "블록체인에 올라간 증명들을 조회", notes = "블록체인 네트워크에 올라간 계약증명들을 트랜젝션 해시값과 반환", response = ApiUtils.ApiResult.class)
    @GetMapping("/contracts/block")
    public ApiUtils.ApiResult<?> findUploadedContracts(){
        User user = (User) request.getSession().getAttribute("loginUser");
        return ApiUtils.success(contractService.getContracts(true, true, true, user));
    }

    @ApiOperation(value = "특정 증명을 반환", notes = "contractId로 특정되는 계약 증명을 반환", response = ApiUtils.ApiResult.class)
    @GetMapping("/contract/{contractId}")
    public ApiUtils.ApiResult<?> findContractsMySignNeeded(@ApiParam(value = "계약증명ID", required = true) @PathVariable long contractId) {
        return ApiUtils.success(contractService.getContract(contractId));
    }
}

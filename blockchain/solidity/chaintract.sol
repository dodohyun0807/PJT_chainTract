// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Chaintract_002 {

    uint256[] chaintractSerialNumbers;
    address manager;
    constructor () {
        manager = msg.sender;
    }
    
    mapping(uint256 => string) serialNumberToEncryptedCode;

    function register(uint256 serialNum, string memory encrypted) public {
        require(keccak256(abi.encodePacked(serialNumberToEncryptedCode[serialNum])) == keccak256(abi.encodePacked("")));
        chaintractSerialNumbers.push(serialNum);
        serialNumberToEncryptedCode[serialNum] = encrypted;
    }

    function verify(uint256 serialNum) public view returns(string memory) {
        return serialNumberToEncryptedCode[serialNum];
    }

}

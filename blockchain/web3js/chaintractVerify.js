const Web3 = require('web3');
const ENDPOINT = 'http://localhost:8545';
const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));

const serialNum = "002";

// account[3] : 0xa6e96c314aa5e1a829e2eaa0be76ed1577900979 (deployer)

const address = "0xa6e96c314aa5e1a829e2eaa0be76ed1577900979"
const CA = "0xECbEac7251521C87fEDF33e4c2C95D7ED9323e86"
const ABI = [
              {
                "inputs": [
                  {
                    "internalType": "uint256",
                    "name": "serialNum",
                    "type": "uint256"
                  },
                  {
                    "internalType": "string",
                    "name": "encrypted",
                    "type": "string"
                  }
                ],
                "name": "register",
                "outputs": [],
                "stateMutability": "nonpayable",
                "type": "function"
              },
              {
                "inputs": [],
                "stateMutability": "nonpayable",
                "type": "constructor"
              },
              {
                "inputs": [
                  {
                    "internalType": "uint256",
                    "name": "serialNum",
                    "type": "uint256"
                  }
                ],
                "name": "verify",
                "outputs": [
                  {
                    "internalType": "string",
                    "name": "",
                    "type": "string"
                  }
                ],
                "stateMutability": "view",
                "type": "function"
              }
            ];

const verify = () => {
    const CONTRACT_ADDRESS = CA;
    const fromAddress = address;
    const test001contract = new web3.eth.Contract(ABI, CONTRACT_ADDRESS);

    test001contract.methods.verify(serialNum)
    .call({from: fromAddress})
    .then(code => console.log("Encrypted Code for Serial No.", serialNum, "is :", code));
};

verify()

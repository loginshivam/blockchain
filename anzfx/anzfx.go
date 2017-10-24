
package main

/* Imports
 * 4 utility libraries for formatting, handling bytes, reading and writing JSON, and string manipulation
 * 2 specific Hyperledger Fabric specific libraries for Smart Contracts
 */
import (
        "encoding/json"
        "fmt"
        "strconv"

        "github.com/hyperledger/fabric/core/chaincode/shim"
        sc "github.com/hyperledger/fabric/protos/peer"
)

// Define the Smart Contract structure
type SmartContract struct {
}

type Trade struct {
        Sym   string `json:"sym"`
        Price  string `json:"price"`
        Size string `json:"size"`
        Side  string `json:"side"`
}

func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
        return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

        // Retrieve the requested Smart Contract function and arguments
        function, args := APIstub.GetFunctionAndParameters()
        // Route to the appropriate handler function to interact with the ledger appropriately
        if function == "getTrade" {
                return s.getTrade(APIstub, args)
        }   else if function == "initLedger" {
                return s.initLedger(APIstub)
        }   else if function == "executeTrade" {
                return s.executeTrade(APIstub, args)

        }

        return shim.Error("Invalid Smart Contract function name.")
}
func (s *SmartContract) getTrade(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        tradeAsBytes, _ := APIstub.GetState(args[0])
        return shim.Success(tradeAsBytes)
}

func (s *SmartContract) initLedger(APIstub shim.ChaincodeStubInterface) sc.Response {
        trades := []Trade{
                Trade{Sym: "USDAUD", Price: "100", Size: "1000", Side: "S"},
                Trade{Sym: "USDEUR", Price: "200", Size: "2000", Side: "B"},
                Trade{Sym: "USDJPY", Price: "250", Size: "7000", Side: "S"},
                Trade{Sym: "USDCHN", Price: "330", Size: "1200", Side: "B"},
        }

        i := 0
        for i < len(trades) {
                fmt.Println("i is ", i)
                tradeAsBytes, _ := json.Marshal(trades[i])
                APIstub.PutState("TRADE"+strconv.Itoa(i), tradeAsBytes)
                fmt.Println("Added", trades[i])
                i = i + 1
        }

        return shim.Success(nil)
}

func (s *SmartContract) executeTrade(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 5 {
                return shim.Error("Incorrect number of arguments. Expecting 4")
        }

        var trade = Trade{Sym: args[1], Price: args[2], Size: args[3], Side: args[4]}

        tradeAsBytes, _ := json.Marshal(trade)
        APIstub.PutState(args[0], tradeAsBytes)

        return shim.Success(nil)
}

func main() {

        // Create a new Smart Contract
        err := shim.Start(new(SmartContract))
        if err != nil {
                fmt.Printf("Error creating new Smart Contract: %s", err)
        }
}

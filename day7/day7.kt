import java.io.File
import java.io.InputStream

val wire_signals = mutableMapOf<String,Int>()           // calculated wire signals
val wire_inputs = mutableMapOf<String,List<String>>()   // wire instructions that cannot be calculated into a signal
val wire_inputs_remove = mutableListOf<String> ()       // to be removed from wire_inputs after added to wire_signals

const val A = "a"
const val B = "b"

fun main() {

	var inputStream: InputStream = File("input.txt").inputStream()
	inputStream.bufferedReader().forEachLine { cmd ->
		parseCmd(cmd)
	}
	
	var a_signal: Int? = null
	while(a_signal == null) {
		if(wire_inputs.size == 0) {
			System.err.println("Error: No inputs remain to calculate the signal in wire a.")
			break
		}
		for((recieving_wire, input) in wire_inputs) {
			executeCmd(recieving_wire, input)
		}
		for(to_remove in wire_inputs_remove) {
			wire_inputs.remove(to_remove)
		}
		wire_inputs_remove.clear()
		a_signal = wire_signals[A]
	}

	if(a_signal == null) {
		println("Part 1: No signal found in wire a.")
		return
	} else {
		println("Part 1: $a_signal in wire a.")
	}

	wire_signals.clear()
	wire_signals[B] = a_signal
	wire_inputs.clear()

	inputStream = File("input.txt").inputStream()
	inputStream.bufferedReader().forEachLine { cmd ->
		if(!(cmd.split(" -> ")[1] == B)) {
			parseCmd(cmd)
		}
	}

	var new_a_signal: Int? = null
	while(new_a_signal == null) {
		if(wire_inputs.size == 0) {
			System.err.println("Error: No inputs remain to calculate the signal in the new wire a.")
			break
		}
		for((recieving_wire, input) in wire_inputs) {
			executeCmd(recieving_wire, input)
		}
		for(to_remove in wire_inputs_remove) {
			wire_inputs.remove(to_remove)
		}
		wire_inputs_remove.clear()
		new_a_signal = wire_signals[A]
	}

	if(new_a_signal == null) {
		println("Part 2: No signal found in the new wire a.")
		return
	} else {
		println("Part 2: $new_a_signal in new wire a.")
	}
}


fun parseCmd(cmd: String) {
    val wire_sides = cmd.split(" -> ")
    if(wire_sides.size != 2) {
        System.err.println("Error: Unable to parse command.")
        return
    }
    val recieving_wire = wire_sides[1]
    val inputs = wire_sides[0].split(" ")
    executeCmd(recieving_wire, inputs)
}


fun executeCmd(recieving_wire: String, inputs: List<String>) {
    if (!inputsHaveSignal(inputs)) wire_inputs[recieving_wire] = inputs
    else calculateWireSignal(recieving_wire, inputs)
}


fun inputsHaveSignal(inputs: List<String>): Boolean {
	for(input in inputs)
		if(input[0].isLowerCase())
			if(!wire_signals.contains(input))
				return false
	return true
}


fun calculateWireSignal(recieving_wire: String, inputs: List<String>) {
    var operation: Operation? = Operation.NONE
    val input_values = mutableListOf<Int>()
    for(input in inputs) {
        if(input[0].isUpperCase()) {
            operation = Operation.fromInput(input)
        } else if(input[0].isLowerCase()) {
            val input_value = wire_signals[input]
            if(input_value == null) {
                System.err.println("Error: Signal from wire $input is not found.")
                wire_inputs[recieving_wire] = inputs
                return
            }
            input_values.add(input_value)
        } else {
            val input_value = input.toIntOrNull()
            if(input_value == null) {
                System.err.println("Error: Instruction signal value $input could not be parsed.")
                return
            }
            input_values.add(input_value)
        }
    }
    BitwiseOperation.fromOperation(operation).calculate(recieving_wire, input_values)
}


enum class Operation {
	AND,
	OR,
	RSHIFT,
	LSHIFT,
	NOT,
	NONE;
	
	companion object {
		fun fromInput(value: String): Operation? {
			return values().find { it.name.equals(value, ignoreCase = true) } ?: Operation.NONE
		}
	}
}


sealed class BitwiseOperation {
	companion object {
		fun fromOperation(operation: Operation?): BitwiseOperation {
			return when (operation) {
				Operation.AND -> AND
				Operation.OR -> OR
				Operation.RSHIFT -> RSHIFT
				Operation.LSHIFT -> LSHIFT
				Operation.NOT -> NOT
				else -> NONE
			}
		}
	}
	
	abstract fun calculate(recieving_wire: String, input_values: MutableList<Int>)

	object AND : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 2) {
				System.err.println("Insufficient inputs for AND operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0] and input_values[1]
			wire_inputs_remove.add(recieving_wire)
		}
	}

	object OR : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 2) {
				System.err.println("Insufficient inputs for OR operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0] or input_values[1]
			wire_inputs_remove.add(recieving_wire)
		}
	}

	object RSHIFT : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 2) {
				System.err.println("Insufficient inputs for RSHIFT operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0] shr input_values[1]
			wire_inputs_remove.add(recieving_wire)
		}
	}

	object LSHIFT : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 2) {
				System.err.println("Insufficient inputs for LSHIFT operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0] shl input_values[1]
			wire_inputs_remove.add(recieving_wire)
		}
	}

	object NOT : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 1) {
				System.err.println("Insufficient input for NOT operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0].inv()
			wire_inputs_remove.add(recieving_wire)
		}
	}
	
	object NONE : BitwiseOperation() {
		override fun calculate(recieving_wire: String, input_values: MutableList<Int>) {
			if(input_values.size != 1) {
				System.err.println("Insufficient input for single wire operation: ${input_values}.")
				return
			}
			wire_signals[recieving_wire] = input_values[0]
			wire_inputs_remove.add(recieving_wire)
		}
	}
}


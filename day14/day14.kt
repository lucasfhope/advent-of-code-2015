import java.io.File
import java.io.InputStream


fun main(args: Array<String>) {

    if (args.size != 1) {
        System.err.println("Usage: [competetion time] expected as command line arguments.")
        return
    }

    val competetionTime = args[0].toInt()
    val competingReindeer = mutableListOf<Reindeer>()

    var inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        competingReindeer.add(newReindeer(str))
    }

    var timeRemaining = competetionTime
    while(timeRemaining > 0) {
        competingReindeer.forEach { reindeer ->
            reindeer.incrementTime()
        }
        awardPoint(competingReindeer)
        timeRemaining--
    }

    var longestDistanceTraveled = Int.MIN_VALUE
    var mostAccumulatedPoints = Int.MIN_VALUE
    competingReindeer.forEach { reindeer ->
        if(reindeer.distanceTraveled > longestDistanceTraveled) longestDistanceTraveled = reindeer.distanceTraveled
        if(reindeer.totalPoints > mostAccumulatedPoints) mostAccumulatedPoints = reindeer.totalPoints
    }

    println(longestDistanceTraveled)
    print(mostAccumulatedPoints)

}

fun awardPoint(reindeer: List<Reindeer>) {
    var furthestReindeer = reindeer[0]
    for(rd in reindeer) {
        if(rd.distanceTraveled > furthestReindeer.distanceTraveled) furthestReindeer = rd
    }
    furthestReindeer.totalPoints++
}

fun newReindeer(inputString: String): Reindeer {
    val splitInput = inputString.split(" ")
    val reindeerName = splitInput[0]
    val reindeerSpeed = splitInput[3].toInt()
    val reindeerDuration = splitInput[6].toInt()
    val reindeerRestTime = splitInput[13].toInt()
    return Reindeer(reindeerName,reindeerSpeed,reindeerDuration,reindeerRestTime)
}

class Reindeer(inputName: String, inputSpeed: Int, inputDuration: Int, inputRest: Int) {

    val name = inputName

    private val speed = inputSpeed            // in km/s
    private val duration = inputDuration      // in s
    private val restTime = inputRest          // in s

    private var nextFlight = 0
    private var nextRest = duration
    private var isFlying = true

    var distanceTraveled = 0          // in km
    var totalPoints = 0
    var currentTime = 0               // in s

    fun incrementTime() {
        currentTime++
        if(isFlying) {
            distanceTraveled += speed
            if (--nextRest == 0) {
                isFlying = false
                nextFlight = restTime
            }
        } else {
            if(--nextFlight == 0) {
                isFlying = true
                nextRest = duration
            }
        }
    }

}
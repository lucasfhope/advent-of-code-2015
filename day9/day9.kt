
import java.io.File
import java.io.InputStream

val location_graph = Graph()
var shortest_path_distance = Int.MAX_VALUE
var longest_path_distance = Int.MIN_VALUE

fun main() {
    val inputStream: InputStream = File("input.txt").inputStream()
    inputStream.bufferedReader().forEachLine { str ->
        addToGraph(str)
    }
    for(i in 0 until location_graph.locations.size) {
        findShortestAndLongestPath(location_graph.locations[i])
    }
    println("The shortest path is $shortest_path_distance.")
    println("The longest path is $longest_path_distance.")
}

fun addToGraph(input: String) {
    val parsedInput= input.split(" ")
    if (parsedInput.size != 5) {
        System.err.println("Error: Unable to parse input.")
        return
    }

    val source = Location(parsedInput[0])
    if(!location_graph.isLocation(source)) {
        location_graph.addLocation(source)
    }

    val destination = Location(parsedInput[2])
    if(!location_graph.isLocation(destination)) {
        location_graph.addLocation(destination)
    }

    val distance = parsedInput[4].toInt()
    location_graph.addEdge(Edge(source, destination, distance))
    location_graph.addEdge(Edge(destination,source,distance))
}

fun findShortestAndLongestPath(location: Location) {
    val path = listOf(location)
    addNeighborsToPath(location, path)
}

fun addNeighborsToPath(location: Location, path: List<Location>) {
    for(neighbor in location_graph.getNeighbours(location)) {
        if(neighbor !in path) {
            checkPath(path + neighbor)
        }
    }
}

fun checkPath(path: List<Location>) {
    if(path.size == location_graph.locations.size) {
        checkDistance(path)
    } else {
        addNeighborsToPath(path[path.size-1], path)
    }
}

fun checkDistance(path: List<Location>) {
    var distance = 0
    for(i in 0 until path.size-1) {
        distance += location_graph.getDistance(path[i],path[i+1])
    }
    if(shortest_path_distance > distance) shortest_path_distance = distance
    if(longest_path_distance < distance) longest_path_distance = distance
}


data class Location (
    val name: String
)

data class Edge (
    val source: Location,
    val destination: Location,
    val distance: Int
)

class Graph {
    val locations = mutableListOf<Location>()
    val edges = mutableListOf<Edge>()
    val visited = mutableListOf<Location>()

    fun addLocation(location: Location) {
        locations.add(location)
    }

    fun isLocation(location: Location): Boolean {
        return location in locations
    }

    fun addEdge(edge: Edge) {
        edges.add(edge)
    }

    fun getNeighbours(location: Location): List<Location> {
        return edges.filter { it.source == location }.map { it.destination }
    }

    fun getDistance(source: Location, destination: Location): Int {
        return edges.firstOrNull { it.source == source && it.destination == destination }?.distance ?: -1
    }
}















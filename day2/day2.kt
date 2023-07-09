import java.io.File
import java.io.InputStream

fun main() { 

    val inputStream: InputStream = File("input.txt").inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) } 

    var wrapping_paper_total = 0
    var ribbon_total = 0
    
    lineList.forEach{
        val length_width_height = it.split("x")
        
        if(length_width_height.count() != 3) {
            System.err.println("Parsing Error: Unable to parse dimension $it.")
            return
        }

        val l = length_width_height[0].toInt()
        val w = length_width_height[1].toInt()
        val h = length_width_height[2].toInt()

        val lw = l*w
        val wh = w*h
        val hl = h*l

        wrapping_paper_total += (2*lw + 2*wh + 2*hl + minOf(lw, minOf(wh, hl)))
        ribbon_total += (l*w*h) + (2*l + 2*w + 2*h) - 2*maxOf(l, maxOf(w,h))
    }

    println("The elves should order $wrapping_paper_total feet of wrapping paper.")    /* 1588178 */
    println("The elves should order $ribbon_total feet of ribbon.")                    /* 3783758 */
}
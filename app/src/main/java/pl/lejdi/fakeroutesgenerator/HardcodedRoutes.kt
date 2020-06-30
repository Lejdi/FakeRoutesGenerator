package pl.lejdi.fakeroutesgenerator

//routes hardcoded in project files
class HardcodedRoutes {
    companion object{
        val r1 = javaClass.getResourceAsStream("/route1")!!.bufferedReader().use { it.readText() }
        val r2 = javaClass.getResourceAsStream("/route2")!!.bufferedReader().use { it.readText() }
        val r3 = javaClass.getResourceAsStream("/route3")!!.bufferedReader().use { it.readText() }
    }
}

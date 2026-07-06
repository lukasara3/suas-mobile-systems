package com.example.tutorial2exercise3

class Catalog {
    val statements = listOf(
        Statement("Brazil is the country with most World Cup Trophies", true),
        Statement("The 2014 FIFA World Cup was hosted by Russia.", false),
        Statement("The only player to score in every single game in a World Cup edition is Jairzinho 'The Hurrican'", true),
        Statement("Pelé won three FIFA World Cups as a player.", true),
        Statement("The FIFA World Cup is held every two years.", false),
    )

    var currentIndex: Int = 0

    fun getCurrentStatement(): Statement?{
        if(statements.isEmpty() || currentIndex > statements.lastIndex){
            return null
        }
        return statements[currentIndex]
    }

    fun jumpToNextStatement(){
        currentIndex++
    }






}
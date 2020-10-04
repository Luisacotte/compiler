package co.edu.uniquindio.compilers.test

class LexicalAnalyzer (var sourceCode:String  ){
    var actualPosition = 0
    var currentCharacter = sourceCode[0] //Primer caracter del código fuente
    var tokenList = ArrayList<Token>()
    var endCode = 0.toChar()
    var currentRow = 0//PRueba
    var currentColumn = 0
    fun analyze(){
        while(currentCharacter!=endCode){

            if(currentCharacter == ' '|| currentCharacter=='\t'||currentCharacter=='\n'){
                setNextCharacter()
                continue
            }
            if(isInteger()) continue
            if(isDecimal()) continue

            if(isIdentifier()) continue
            storeToken(""+currentCharacter, Category.DESCONOCIDO, currentRow, currentColumn)
            setNextCharacter()
        }
    }

    fun isDecimal():Boolean{
        if(currentCharacter=='.'||currentCharacter.isDigit()){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            if(currentCharacter=='.'){
                lexema += currentCharacter
                setNextCharacter()
                if(currentCharacter.isDigit()){
                    lexema+=currentCharacter
                    setNextCharacter()
                }
            }else{
                lexema += currentCharacter
                setNextCharacter()
                while(currentCharacter.isDigit()){
                    lexema+=currentCharacter
                    setNextCharacter()
                }
                if(currentCharacter=='.'){

                    lexema+=currentCharacter
                    setNextCharacter()


                }
            }
            while(currentCharacter.isDigit()){
                lexema+=currentCharacter
                setNextCharacter()
            }
            storeToken(lexema, Category.DECIMAL, initialRow, initialColumn)
            return true
        }
        return false
    }
    fun doBackTracking(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){
        actualPosition = posicionInicial
        currentRow = filaInicial
        currentColumn = columnaInicial

        currentCharacter = sourceCode[actualPosition]
    }


    fun isInteger():Boolean{
        if(currentCharacter.isDigit()){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition

            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while(currentCharacter.isDigit()){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }

            if(currentCharacter=='.'){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }


            storeToken(lexema, Category.ENTERO, initialRow, initialColumn)
            return true
        }
        //RI -> Rechazo Inmediato
        return false
    }

    fun isIdentifier():Boolean{
        if(currentCharacter.isLetter()||currentCharacter=='$'||currentCharacter=='_'){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn


            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while(currentCharacter.isLetter()||currentCharacter=='$'||currentCharacter=='_'||currentCharacter.isDigit()){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }
            storeToken(lexema, Category.IDENTIFICADOR, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * Único método que debe hacer:
     * aumentar posición,
     * actualizar caracter actual
     * verificar que no se desborde el codigo fuente
     *
     */
    fun setNextCharacter(){
        if(actualPosition==sourceCode.length-1){
            currentCharacter = endCode
        }else{

            if(currentCharacter == '\n'){
                currentRow++
                currentColumn=0
            }else{
                currentColumn++
            }

            actualPosition++
            currentCharacter = sourceCode[actualPosition]
        }
    }

    /**
     * Almacena tokens en la lista de tokens
     */
    fun storeToken(lexema:String, category:Category, row:Int, column:Int) = tokenList.add(Token(lexema, category, row, column))
}
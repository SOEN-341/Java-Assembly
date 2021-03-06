import java.io.*;

public class Scanner{
    private Reader reader;
    private int line = 1;
    private int column = 1;
    private int character;
    private int index=0;
    private ErrorReporter reporter;
    private SymbolTable symbolTable;
    private boolean labelCheck = false;
    public ErrorReporter getReporter() {
        return reporter;
    }
    public SymbolTable getSymbolTable(){return symbolTable;}


    public Scanner(String fileName,ErrorReporter reporter,SymbolTable symbolTable) {
        reader = new Reader(new File(fileName));
        this.reporter = reporter;
        this.symbolTable = symbolTable;

        try {
            this.character = reader.readChar();
            this.addLabel(character,symbolTable);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addLabel(int ch, SymbolTable symbolTable) throws IOException{

        while (ch != -1) {
            String label="";
            if(Character.isLetter(ch)) {
                while (ch != 32 && ch != 10) {
                    label += (char) ch;
                    ch = reader.readChar();
                }
                symbolTable.addlabel(label, -1);
            }
            else{
                while (ch != 10) { ch = reader.readChar();
                }
            }
            ch = reader.readChar();
        }
        reader.closeInputStream();
        reader.openInputStream();
    }

    public Token scanToken()  {
        String mnemonic = "";
        String comment="";
        String operand="";
        String label ="";
        ErrorMsg error = null;
        Token token = null;


        try {
            do{
                // checking for a label
                if (index == 0 && Character.isLetter(character)){
                    while(character!= 32 && character != 10){
                        label += (char)character;
                        character = reader.readChar();
                        index++;
                        if (character == -1) {
                            error = new ErrorMsg("Error: eof in string", new Position(line, column));
                            this.reporter.record(error);
                            break;
                        }
                    }
                    label = label.trim();
                    token = new Token(new Position(line, column),label,TokenType.Label);
                    column++;
                    labelCheck = true;
                    break;
                }
                // checking for a semicolon to scan a comment
                if (character == 59){
                    while(character != 10 && character != -1){
                        comment += (char)character;
                        character = reader.readChar();
                        index++;
                    }
                    if (comment.trim().equals(";")) {
                        error = new ErrorMsg("Error: eol in string", new Position(line, column));
                        this.reporter.record(error);
                        column++;
                    }
                    else if (comment.trim().equals(";") && character == -1) {
                        error = new ErrorMsg("Error: eof in string", new Position(line, column));
                        this.reporter.record(error);
                    }
                    else {
                        comment = comment.trim();
                        token = new Token(new Position(line, column),comment,TokenType.Comment);
                        column++;
                        break;
                    }
                }
                // checking for a mnemonic
                if (Character.isLetter(character) && index > 0){
                    if(column == 2 && labelCheck == false) {
                        while(character!= 32 && character != 10) {
                            operand += (char)character;
                            character = reader.readChar();
                            index++;
                            if (character == -1) {
                                error = new ErrorMsg("Error: eof in string", new Position(line, column));
                                this.reporter.record(error);
                                break;
                            }
                        }
                        operand = operand.trim();
                        token = new Token(new Position(line, column),operand,TokenType.Operand);
                        column++;
                        break;
                    }
                    if(column == 3 && labelCheck == true) {
                        while(character!= 32 && character != 10) {
                            operand += (char)character;
                            character = reader.readChar();
                            index++;
                            if (character == -1) {
                                error = new ErrorMsg("Error: eof in string", new Position(line, column));
                                this.reporter.record(error);
                                labelCheck =false;
                                break;
                            }
                        }
                        operand = operand.trim();
                        token = new Token(new Position(line, column),operand,TokenType.Operand);
                        column++;
                        break;
                    }
                    while(character!= 32 && character != 10){
                        mnemonic += (char)character;
                        character = reader.readChar();
                        index++;
                        if (character == -1) {
                            error = new ErrorMsg("Error: eof in string", new Position(line, column));
                            this.reporter.record(error);
                            break;
                        }
                    }
                    mnemonic = mnemonic.trim();
                    symbolTable.addMnemonic(mnemonic);
                    token = new Token(new Position(line, column),mnemonic,TokenType.Mnemonic);
                    column++;
                    break;
                }
                // Mnemoic .cstring
                if(character == 46){
                    while(character!= 32 && character != 10){
                        mnemonic += (char)character;
                        character = reader.readChar();
                        index++;
                        if (character == -1) {
                            error = new ErrorMsg("Error: eof in string", new Position(line, column));
                            this.reporter.record(error);
                            break;
                        }
                    }
                    mnemonic = mnemonic.trim();
                    symbolTable.addMnemonic(mnemonic);
                    token = new Token(new Position(line, column),mnemonic,TokenType.Mnemonic);
                    column++;
                    break;
                }

                // checking for an integer
                if (Character.isDigit(character)){
                    while(character!= 32 && character != 10) {
                        operand += (char)character;
                        character = reader.readChar();
                        index++;
                        if (character == -1) {
                            error = new ErrorMsg("Error: eof in string", new Position(line, column));
                            this.reporter.record(error);
                            break;
                        }
                    }
                    operand = operand.trim();
                    token = new Token(new Position(line, column),operand,TokenType.Operand);
                    // System.out.println(character);
                    column++;
                    break;
                }
                // checking for a signed number
                if (character == 45 || character == 43) {
                    operand += (char)character;
                    character = reader.readChar();
                    index++;
                    while (Character.isDigit(character)) {
                        operand += (char)character;
                        character = reader.readChar();
                        index++;
                    }
                    if (operand.trim().equals("-") || operand.trim().equals("+")) {
                        error = new ErrorMsg("Error: Invalid character", new Position(line, column));
                        this.reporter.record(error);
                        column++;
                    }
                    else {
                        operand = operand.trim();
                        token = new Token(new Position(line, column),operand,TokenType.Operand);
                        column++;
                        break;
                    }
                }

                if(character == 34){
                    if(column == 2 &&labelCheck == false) {
                        while (character != 32 && character != 10) {
                            operand += (char) character;
                            character = reader.readChar();
                            index++;
                            if (character == -1) {
                                error = new ErrorMsg("Error: eof in string", new Position(line, column));
                                this.reporter.record(error);
                                break;
                            }
                        }
                        operand = operand.trim();
                        token = new Token(new Position(line, column), operand, TokenType.Operand);
                        column++;
                        break;
                    }
                    if(column == 3 &&labelCheck == true){
                        while (character != 32 && character != 10) {
                            operand += (char) character;
                            character = reader.readChar();
                            index++;
                            if (character == -1) {
                                error = new ErrorMsg("Error: eof in string", new Position(line, column));
                                this.reporter.record(error);
                                labelCheck = false;
                                break;
                            }
                        }
                        operand = operand.trim();
                        token = new Token(new Position(line, column), operand, TokenType.Operand);
                        column++;
                        break;
                    }
                }
                // checking for invalid characters.
                if((character >= 33 && character <= 47) || character == 58 || (character >= 60 && character <= 64)
                        || (character >= 91 && character <= 96) || (character >= 123 && character <= 126)) {

                    error = new ErrorMsg("Invalid character", new Position(line, column));
                    this.reporter.record(error);
                    column++;
                }
                // end-of-line
                if (character == 10){
                    token = new Token(new Position(line, column),"EOL",TokenType.EOL);
                    column = 1;
                    line++;
                    this.character = reader.readChar();
                    index=0;
                    labelCheck = false;
                    break;
                }
                // end-of-file
                if (character == -1){
                    token = new Token(new Position(line, column),"EOF",TokenType.EOF);
                    reader.closeInputStream();
                    break;
                }

                character = reader.readChar();
                index++;

            }while(character != -1);

        }

        catch (IOException e){
            e.printStackTrace();
        }
        return token;
    }

    public static void main(String[] args) {
        ErrorReporter errorReporter = new ErrorReporter();
        SymbolTable symbolTable = new SymbolTable();
        Scanner sc1 = new  Scanner("rela01.asm",errorReporter,symbolTable);
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());
        System.out.println(sc1.scanToken());


    }


}

// TestToken.java

public class TestToken {
    public static void main(String[] args) throws Exception {
        Position p1 = new Position(1, 2);   // (line, col)
        Position p2 = new Position(2, 4);
        Position p3 = new Position(3, 6);

        Token t1 = new Token(p1, "halt", TokenType.Mnemonic);
        Token t2 = new Token(p2, "pop",  TokenType.Mnemonic);
        Token t3 = new Token(p3, "dup",  TokenType.Mnemonic);

        System.out.println("Test Token");
        System.out.println("[halt(1,2)=Mnemonic][pop(2,4)=Mnemonic][dup(3,6)=Mnemonic]");

        System.out.print(t1);
        System.out.print(t2);
        System.out.print(t3);
        System.out.println();
    }
}

import java.io.IOException;
public class TestCodeGenerator {
    public static void main(String[] args) throws IOException{


        //System.out.println(new CodeGenerator(null, new SymbolTable(), new File("TestInherentMnemonics.asm")).lineStatetolst(5,5, new LineStatement(null, "exit","0", null),new SymbolTable()));
        //System.out.println("Test generateLst");
        InterRep IR = new InterRep();
        IR.add(new LineStatement("End", "br.i8", "End", "testing"));
        IR.add(new LineStatement(null, ".cstring", "A1", "testing"));
        SymbolTable sT = new SymbolTable();
        sT.addlabel("End", 6);
        sT.addMnemonic("br.i8");
        System.out.println("Test Generate RelativeCode");
        CodeGenerator cG = new CodeGenerator(IR, sT, null, false);
        System.out.println(cG.generateRelativeCode(6,IR.getLS(0), false));
        System.out.println("E0 00");
        System.out.println("Test RelativeString1");
        System.out.println(cG.RelativeString(4, 6, IR.getLS(0), false));
        System.out.println("0004 0006 E0 00         End           br.i8      End      ;testing");
        System.out.println("Test RelativeString2");
        System.out.println(cG.RelativeString(4, 6, IR.getLS(1), false));
        System.out.println("0004 0006 41 31 00                    .cstring    A1      ;testing");





//        ErrorReporter reporter=new ErrorReporter();
//        InterRep IR = new Parser(new Scanner("TestImmediate.asm",reporter, new SymbolTable())).generates();
//
//        new CodeGenerator(IR, new SymbolTable(), new File("copiedTestInherentMnemonics.asm")).generateListing();
//
//        InputStream is = new FileInputStream(new File ("TestInherentMnemonics.lst"));

//        int i = 0;
//        int count = 0;
//        String actual = "";
//        while(i != -1){ // end of file
//            i = is.read();
//            actual += (char)i;
//            if(i == '\n'){ // counting lines
//                count++;
//            }
//            if(count == 2){ // break when two lines are read
//                break;
//            }
//        }
//        is.close();
//        System.out.println("Line Addr Code          Label         Mne   Operand       Comments1    0000 00                          halt");
//        System.out.println(actual.replace("\n", ""));
    }


}

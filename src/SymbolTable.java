import java.util.Hashtable;
public class SymbolTable implements ISymbolTable{
    private Hashtable Table;
    // This is going to be the hardcode for the 25 mne
    public SymbolTable(){
        this.Table = new Hashtable();
        Table.put("hlt", new Mnemonic("hlt", 0x00)); ;
        Table.put("pop", new Mnemonic("pop", 0x01));
        Table.put("exit", new Mnemonic("exit", 0x03));
        Table.put("ret", new Mnemonic("ret", 0x04));
        Table.put("not", new Mnemonic("not", 0x0C));
        Table.put("and", new Mnemonic("and", 0x0D));
        Table.put("or", new Mnemonic("or", 0x0E));
        Table.put("xor", new Mnemonic("xor", 0x0F));
        Table.put("neg", new Mnemonic("neg", 0x10));
        Table.put("inc", new Mnemonic("inc", 0x11));
        Table.put("dec", new Mnemonic("dec", 0x12));
        Table.put("add", new Mnemonic("add", 0x13));
        Table.put("sub", new Mnemonic("sub", 0x14));
        Table.put("mul", new Mnemonic("mul", 0x15));
        Table.put("div", new Mnemonic("div", 0x16));
        Table.put("rem", new Mnemonic("rem", 0x17));
        Table.put("shl", new Mnemonic("shl", 0x18));
        Table.put("shr", new Mnemonic("shr", 0x19));
        Table.put("teq", new Mnemonic("teq", 0x1A));
        Table.put("tne", new Mnemonic("tne", 0x1B));
        Table.put("tlt", new Mnemonic("tlt", 0x1C));
        Table.put("tgt", new Mnemonic("tgt", 0x1D));
        Table.put("tle", new Mnemonic("tle", 0x1E));
        Table.put("tge", new Mnemonic("tge", 0x1F));

    }

    public int getOpcode(String mnemonic){
        return ((Mnemonic)Table.get(mnemonic)).getOpcode();
    }


}


// String upcode = Table.getOpcode(arrylist.get(i).getMne());
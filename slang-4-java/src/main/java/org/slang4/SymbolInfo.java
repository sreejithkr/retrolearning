package org.slang4;

import org.w3c.dom.TypeInfo;

public class SymbolInfo {
    public String symbolName;   // Symbol Name
    public TypeInfo type;      // Data type
    public String strVal;      // memory to hold string
    public double dblVal;      // memory to hold double
    public Boolean boolVal;      // memory to hold boolean
    //
    // Added in STEP 5 to store offset
    // in the TypeBuilder.BuildLocal table
    // Only used by the compiler..interpreter
    // just ignores it..!
    public int loc_position = 0;
}



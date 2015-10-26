package com.ardublock.translator.block.girts;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PullupSwitch extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE = 
	    "boolean __ardublockPullupDigitalRead(int pinNumber)\n"+
	    "{\n"+
	    "pinMode(pinNumber, INPUT);\n"+
	    "digitalWrite(pinNumber, HIGH); // enable pullup\n"+
	    "return digitalRead(pinNumber);\n"+
	    "}\n\n";
	
	public PullupSwitch(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		String ret = "__ardublockPullupDigitalRead(" + translatorBlock.toCode() + ")";
		return codePrefix + ret + codeSuffix;
	}
	
}

package com.ardublock.translator.block.girts;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DFRobotRomeoButton extends TranslatorBlock
{
	private static final String DEFINITIONS = 
		"int get_romeo_button(int input){\n" + 
		"  const int NUM_KEYS = 5;\n" + 
		"  static int adc_key_val[NUM_KEYS] ={50, 200, 400, 600, 800};\n" + 
		"  for (int k = 0; k < NUM_KEYS; k++){\n" + 
		"    if (input < adc_key_val[k]){\n" + 
		"      return k+1;\n" + 
		"    }\n" + 
		" }\n" + 
		" return 0;\n" + 
		"}\n" + 
		"bool is_romeo_button_pressed(int button_num){ // 1..5\n" + 
		"  int adc_in = analogRead(7);\n" + 
		"  int button = get_romeo_button(adc_in);\n" + 
		"  return button == button_num;\n" + 
		"}\n" + 
		"";
	
	public DFRobotRomeoButton(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(DEFINITIONS);
		TranslatorBlock parameter = this.getRequiredTranslatorBlockAtSocket(0);
		String code = "is_romeo_button_pressed(" + parameter.toCode() + ")";
		return codePrefix + code + codeSuffix;
	}
	
}

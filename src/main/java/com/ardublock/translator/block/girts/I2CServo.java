package com.ardublock.translator.block.girts;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.VariableNumberBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class I2CServo extends TranslatorBlock {

	public I2CServo(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String pinNumber = tb.toCode();

		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String servoAngle = tb.toCode();
		if ( tb instanceof NumberBlock )
		{
		    int servoAngleInt = Integer.parseInt(servoAngle);
		    if (servoAngleInt < 210) {
			    throw new BlockException(this.blockId, "the Angle must be 210..550");
		    }
		    if (servoAngleInt > 550) {
			    throw new BlockException(this.blockId, "the Angle must be 210..550");
		    }
		} else if ( tb instanceof VariableNumberBlock ) {
		    // ok, go on
		} else {
		    throw new BlockException(this.blockId, "the Angle must be a number");
		}
		
		String ret = String.format("servos.setPWM(%s,0,%s);",pinNumber,servoAngle);

		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("Adafruit_PWMServoDriver.h");
		translator.addDefinitionCommand("Adafruit_PWMServoDriver servos;");
		translator.addSetupCommand("servos.begin();");
		translator.addSetupCommand("servos.setPWMFreq(60);");
		return ret;
	}

}

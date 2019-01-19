/**
 * Copyright 2014-2019 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.tabletop.dice.notation.transformer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.tabletop.dice.notation.DiceNotationExpression;
import com.bernardomg.tabletop.dice.notation.TransformableDiceNotationExpression;
import com.bernardomg.tabletop.dice.notation.operand.ConstantOperand;
import com.bernardomg.tabletop.dice.notation.operand.DiceOperand;
import com.bernardomg.tabletop.dice.notation.operation.BinaryOperation;
import com.bernardomg.tabletop.dice.roller.DefaultRoller;
import com.bernardomg.tabletop.dice.roller.Roller;

/**
 * Dice notation expression which simulates rolling the expression.
 * <p>
 * As some values, such as dice, represent random numbers the transformer may
 * not return the same result each time it is executed for the same expression.
 * <p>
 * The random value will be generated by a {@code Roller} contained in the
 * transformer, which can be set through the constructor. Otherwise the default
 * one, a {@link DefaultRoller}, will be used.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class RollerTransformer
        implements DiceNotationTransformer<Integer> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RollerTransformer.class);

    /**
     * Roller to generate random values from dice.
     */
    private final Roller        roller;

    /**
     * Default constructor.
     */
    public RollerTransformer() {
        super();

        roller = new DefaultRoller();
    }

    /**
     * Constructs a transformer using the received roller for simulating rolls.
     * 
     * @param roll
     *            roller for simulating rolls
     */
    public RollerTransformer(final Roller roll) {
        super();

        roller = checkNotNull(roll, "Received a null pointer as roller");
    }

    @Override
    public final Integer transform(final DiceNotationExpression expression) {
        final Integer result;
        // TODO: Avoid casting

        LOGGER.debug("Transforming expression {}", expression.getClass());
        if (expression instanceof TransformableDiceNotationExpression) {
            result = transform(
                    ((TransformableDiceNotationExpression) expression)
                            .getRoot());
        } else if (expression instanceof BinaryOperation) {
            result = transform((BinaryOperation) expression);
        } else if (expression instanceof ConstantOperand) {
            result = transform((ConstantOperand) expression);
        } else if (expression instanceof DiceOperand) {
            result = transform((DiceOperand) expression);
        } else {
            LOGGER.warn("Unsupported expression");
            result = 0;
        }

        return result;
    }

    /**
     * Generates a random value from a binary operation.
     * <p>
     * It generates a value for both sides of the operation, then applies the
     * operation to these values.
     * 
     * @param operation
     *            operation to transform
     * @return a random value generated from the operation
     */
    private final Integer transform(final BinaryOperation operation) {
        final DiceNotationExpression left;
        final DiceNotationExpression right;
        final Integer leftValue;
        final Integer rightValue;
        final Integer value;
        final BiFunction<Integer, Integer, Integer> func;

        left = operation.getLeft();
        right = operation.getRight();

        func = operation.getOperation();

        LOGGER.debug("Transforming left operand");
        leftValue = transform(left);
        LOGGER.debug("Transforming right operand");
        rightValue = transform(right);

        value = func.apply(leftValue, rightValue);

        return value;
    }

    /**
     * Returns the value from a constant operand.
     * 
     * @param operand
     *            operand to transform
     * @return the constant from the operand
     */
    private final Integer transform(final ConstantOperand operand) {
        return operand.getValue();
    }

    /**
     * Returns the value from a dice operand.
     * <p>
     * This will generate a random value for each die in the dice set. The
     * actual random value will be generated by the dice roller.
     * 
     * @param operand
     *            operand to transform
     * @return a random value generated from the dice
     */
    private final Integer transform(final DiceOperand operand) {
        final Iterable<Integer> rolls;
        Integer total;

        rolls = roller.roll(operand.getDice());

        total = 0;
        for (final Integer roll : rolls) {
            total += roll;
        }

        return total;
    }

}

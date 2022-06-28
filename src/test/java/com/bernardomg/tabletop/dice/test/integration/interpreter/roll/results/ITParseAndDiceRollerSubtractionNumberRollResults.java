/**
 * Copyright 2014-2022 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.bernardomg.tabletop.dice.test.integration.interpreter.roll.results;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.history.RollResult;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.notation.DiceNotationExpression;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;

@DisplayName("DiceRoller returns the expected roll results for subtraction operations using only numbers")
public final class ITParseAndDiceRollerSubtractionNumberRollResults {

    /**
     * Default constructor.
     */
    public ITParseAndDiceRollerSubtractionNumberRollResults() {
        super();
    }

    @Test
    @DisplayName("Returns the expected number of values")
    public final void testParse_Quantity() {
        final DiceNotationExpression operation;
        final String                 notation;
        final Iterable<RollResult>   results;

        notation = "-1-2-3";

        operation = new DefaultDiceParser().parse(notation);

        results = new DiceRoller().transform(operation)
            .getRollResults();

        Assertions.assertEquals(3, StreamSupport.stream(results.spliterator(), false)
            .count());
    }

    @Test
    @DisplayName("Returns the expected total roll")
    public final void testParse_TotalRoll() {
        final DiceNotationExpression expression;
        final String                 notation;
        final RollHistory            history;

        notation = "-1-2-3";

        expression = new DefaultDiceParser().parse(notation);

        history = new DiceRoller().transform(expression);

        Assertions.assertEquals(Integer.valueOf(-6), history.getTotalRoll());
    }

    @Test
    @DisplayName("Returns the expected total rolls")
    public final void testParse_TotalRolls() {
        final DiceNotationExpression expression;
        final Iterator<RollResult>   rolled;
        final String                 notation;
        RollResult                   result;

        notation = "-1-2-3";

        expression = new DefaultDiceParser().parse(notation);

        rolled = new DiceRoller().transform(expression)
            .getRollResults()
            .iterator();

        result = rolled.next();

        Assertions.assertEquals(Integer.valueOf(-1), result.getTotalRoll());

        result = rolled.next();

        Assertions.assertEquals(Integer.valueOf(-2), result.getTotalRoll());

        result = rolled.next();

        Assertions.assertEquals(Integer.valueOf(-3), result.getTotalRoll());
    }

    @Test
    @DisplayName("Returns the expected values for each number")
    public final void testParse_Value() {
        final DiceNotationExpression operation;
        final String                 notation;
        final Iterable<RollResult>   results;
        final Iterator<RollResult>   resultsItr;
        RollResult                   result;
        Iterable<Integer>            rolls;
        Iterator<Integer>            rollValues;

        notation = "-1-2-3";

        operation = new DefaultDiceParser().parse(notation);

        results = new DiceRoller().transform(operation)
            .getRollResults();
        resultsItr = results.iterator();

        Assertions.assertEquals(3, StreamSupport.stream(results.spliterator(), false)
            .count());

        result = resultsItr.next();
        rolls = result.getAllRolls();
        Assertions.assertEquals(1, StreamSupport.stream(rolls.spliterator(), false)
            .count());

        rollValues = rolls.iterator();
        Assertions.assertEquals(Integer.valueOf(-1), rollValues.next());

        result = resultsItr.next();
        rolls = result.getAllRolls();
        Assertions.assertEquals(1, StreamSupport.stream(rolls.spliterator(), false)
            .count());

        rollValues = rolls.iterator();
        Assertions.assertEquals(Integer.valueOf(-2), rollValues.next());

        result = resultsItr.next();
        rolls = result.getAllRolls();
        Assertions.assertEquals(1, StreamSupport.stream(rolls.spliterator(), false)
            .count());

        rollValues = rolls.iterator();
        Assertions.assertEquals(Integer.valueOf(-3), rollValues.next());
    }

}

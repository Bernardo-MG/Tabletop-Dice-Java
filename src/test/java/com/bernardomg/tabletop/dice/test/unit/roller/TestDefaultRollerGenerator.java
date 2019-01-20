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

package com.bernardomg.tabletop.dice.test.unit.roller;

import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.tabletop.dice.Dice;
import com.bernardomg.tabletop.dice.roller.DefaultRoller;
import com.bernardomg.tabletop.dice.roller.Roller;
import com.bernardomg.tabletop.dice.roller.random.NumberGenerator;

/**
 * Units tests for {@link DefaultRoller}, checking that it handles the number
 * generator as expected.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@RunWith(JUnitPlatform.class)
public final class TestDefaultRollerGenerator {

    /**
     * Default constructor.
     */
    public TestDefaultRollerGenerator() {
        super();
    }

    /**
     * Verifies that the roller returns the values generated by its number
     * generator.
     */
    @Test
    public final void testRoll_ReturnsGenerated() {
        final Roller roller;                // Roller being tested
        final Dice dice;                    // Die to roll
        final NumberGenerator generator;    // Number generator used
        final Iterator<Integer> result;     // Roll results

        // Mocks dice
        dice = Mockito.mock(Dice.class);
        Mockito.when(dice.getQuantity()).thenReturn(3);
        Mockito.when(dice.getSides()).thenReturn(6);

        // Mocks generator
        generator = Mockito.mock(NumberGenerator.class);
        Mockito.when(generator.generate(ArgumentMatchers.any())).thenReturn(3,
                5, 1);

        // Initializes roller and generates value
        roller = new DefaultRoller(generator);
        result = roller.roll(dice).iterator();

        Assertions.assertEquals((Integer) 3, result.next());
        Assertions.assertEquals((Integer) 5, result.next());
        Assertions.assertEquals((Integer) 1, result.next());
    }

}

/**
 * Copyright 2014-2016 the original author or authors
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

package com.wandrell.tabletop.dice.roller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;

import com.wandrell.tabletop.dice.Dice;
import com.wandrell.tabletop.dice.random.DefaultNumberGenerator;
import com.wandrell.tabletop.dice.random.NumberGenerator;

/**
 * Generates random integer values from a {@link Dice}.
 * 
 * @author Bernardo Martínez Garrido
 * @see Dice
 */
public final class DefaultRoller implements Roller {

    /**
     * edRoller<V> implements Roller<V> {
     * 
     * /** The random numbers generator.
     * <p>
     * It will use the dice for the interval in which the values should be
     * created.
     */
    private final NumberGenerator generator;

    /**
     * Default constructor.
     */
    public DefaultRoller() {
        super();

        this.generator = new DefaultNumberGenerator();
    }

    /**
     * Constructs an instance with the specified random number generator.
     * 
     * @param generator
     *            the random number generator to use
     */
    public DefaultRoller(final NumberGenerator generator) {
        super();

        this.generator = checkNotNull(generator,
                "Received a null pointer as generator");
    }

    @Override
    public final Collection<Integer> roll(final Dice dice) {
        final Collection<Integer> rolls; // Roll results

        checkNotNull(dice, "Received a null pointer as dice");

        rolls = new LinkedList<Integer>();
        for (Integer i = 0; i < dice.getQuantity(); i++) {
            rolls.add(getNumberGenerator().generate(dice.getSides()));
        }

        return rolls;
    }

    /**
     * Returns the random number generator.
     * 
     * @return the random number generator
     */
    private final NumberGenerator getNumberGenerator() {
        return generator;
    }

}

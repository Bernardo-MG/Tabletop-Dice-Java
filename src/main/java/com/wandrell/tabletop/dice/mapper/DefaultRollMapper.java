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

package com.wandrell.tabletop.dice.mapper;

/**
 * Returns the rolls as received.
 * <p>
 * This is used to skip mapping the values.
 * 
 * @author Bernardo Martínez Garrido
 */
public final class DefaultRollMapper implements RollMapper<Integer> {

	/**
	 * Default constructor.
	 */
	public DefaultRollMapper() {
		super();
	}

	/**
	 * Returns the roll value as received.
	 * 
	 * @param roll
	 *            the roll value
	 * @return the roll value just as received
	 */
	@Override
	public final Integer getValueFor(final Integer roll) {
		return roll;
	}

}

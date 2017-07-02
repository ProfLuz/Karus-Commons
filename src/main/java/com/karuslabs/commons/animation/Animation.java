/*
 * Copyright (C) 2017 Karus Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.karuslabs.commons.animation;

import com.karuslabs.commons.animation.particles.Particles;


public abstract class Animation<GenericParticles extends Particles> implements Runnable {
    
    protected GenericParticles particles;
    
    protected boolean asynchronous;
    protected int iterations;
    protected int period;
    protected int delay;
    
    protected Runnable callback;
    
    
    public Animation(GenericParticles particles, boolean asynchronous, int iterations, int period, int delay, Runnable callback) {
        this.particles = particles;
        this.asynchronous = asynchronous;
        this.iterations = iterations;
        this.period = period;
        this.delay = delay;
    }
    
}
/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.animation.particles.effects;

import com.karuslabs.commons.animation.particles.*;
import com.karuslabs.commons.animation.particles.effect.*;
import com.karuslabs.commons.world.BoundLocation;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import static com.karuslabs.commons.world.Vectors.randomCircle;


public class Cloud implements Task<Cloud, BoundLocation, BoundLocation> {
    
    private Particles cloud;
    private Particles droplets;
    private float size;
    private float radius;
    private double offsetY;
    
    
    public Cloud(Particles cloud, Particles droplets) {
        this(cloud, droplets, 0.7F, 0.7F - 0.1F, 0.8);
    }
    
    public Cloud(Particles cloud, Particles droplets, float size, float radius, double offsetY) {
        this.cloud = cloud;
        this.droplets = droplets;
        this.size = size;
        this.radius = radius;
        this.offsetY = offsetY;
    }

    
    @Override
    public void render(Context<BoundLocation, BoundLocation> context) {
        Location location = context.getOrigin().getLocation();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        
        renderCloud(context, location.add(0, offsetY, 0), random, 50);
        renderDroplets(context, location.add(0, 0.2, 0), random, 15);
    }
    
    protected void renderCloud(Context<BoundLocation, BoundLocation> context, Location location, ThreadLocalRandom random, int amount) {
        Vector vector = context.getVector();
        for (int i = 0; i < amount; i++) {
            randomCircle(vector).multiply(random.nextDouble() * size);
            context.render(cloud, location, vector);
        }
    }
    
    protected void renderDroplets(Context<BoundLocation, BoundLocation> context, Location location, ThreadLocalRandom random, int amount) {
        for (int i = 0; i < amount; i++) {
            if (random.nextInt(2) != 1) {
                double x = random.nextDouble() * radius;
                double z = random.nextDouble() * radius;

                context.render(droplets, location.add(x, 0, z));
                context.render(droplets, location.subtract(x * 2, 0, z * 2));
                location.add(x, 0, z);
            }
        }
    }

    @Override
    public Cloud get() {
        return this;
    }
    
}

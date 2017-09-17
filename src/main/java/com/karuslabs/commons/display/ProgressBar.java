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
package com.karuslabs.commons.display;

import com.karuslabs.commons.locale.Translation;

import com.karuslabs.commons.util.concurrent.Promise;

import java.util.*;
import java.util.function.BiConsumer;

import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static java.util.stream.Collectors.toList;


public class ProgressBar extends AbstractBar<List<BossBar>, ProgressBarTask> {
    
    private static final BiConsumer<ProgressBarTask, BossBar> CONSUMER = (task, bar) -> {};

    
    public ProgressBar(Plugin plugin, Translation translation, BiConsumer<ProgressBarTask, BossBar> consumer, BarColor color, BarStyle style, BarFlag[] flags, double progress, long frames, long delay, long period) {
        super(plugin, translation, consumer, color, style, flags, progress, frames, delay, period);
    }

    
    
    @Override
    public Promise<List<BossBar>> render(Collection<Player> players) {
        List<BossBar> bars = players.stream().map(this::create).collect(toList());
                
        ProgressBarTask task = new ProgressBarTask(frames, translation, bars, consumer);
        task.runTaskTimerAsynchronously(plugin, delay, period);
        
        return task;
    }
    
    
    public static ProgressBarBuilder builder(Plugin plugin) {
        return new ProgressBarBuilder(new ProgressBar(plugin, Translation.NONE, CONSUMER, BarColor.BLUE, BarStyle.SEGMENTED_10, FLAGS, 0, 0, 0, 0));
    }
    

    public static class ProgressBarBuilder extends AbstractBuilder<ProgressBarBuilder, ProgressBar, ProgressBarTask> {

        public ProgressBarBuilder(ProgressBar bar) {
            super(bar);
        }
        
        
        @Override
        protected ProgressBarBuilder getThis() {
            return this;
        }
        
    }
    
}

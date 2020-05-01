package com.goodelephantlab.airwizard.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
    fun pool(count: Int): Scheduler

}
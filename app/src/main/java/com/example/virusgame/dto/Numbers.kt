package com.example.virusgame.dto

import kotlin.collections.ArrayList

object Numbers {
    private val NUMBER_1 = arrayListOf(
        2+3,
        5, 7
    )


    fun getImages2(rstGmLevel: Int, stimulateCnt: Int): ArrayList<Picture> {
        val imgs: ArrayList<Int>
        val returns = ArrayList<Picture>()
        when (rstGmLevel) {
            1 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 1) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            2 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    i++
                }
            }
            3 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 2) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            4 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    i++
                }
            }
            5 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 2 || i == 3) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            6 -> {
                imgs = NUMBER_1
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 4) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            7 -> {
                imgs = ArrayList()
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    i++
                }
            }
            8 -> {
                imgs = ArrayList()
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 4 || i == 5) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            9 -> {
                imgs = ArrayList()
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    if (i == 6) {
                        returns.add(Picture(imgs[i], i.toString()))
                    }
                    i++
                }
            }
            10 -> {
                imgs = ArrayList()
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.addAll(NUMBER_1)
                imgs.shuffle()
                var i = 0
                while (i < stimulateCnt) {
                    returns.add(Picture(imgs[i], i.toString()))
                    i++
                }
            }
        }
        return returns
    }

}
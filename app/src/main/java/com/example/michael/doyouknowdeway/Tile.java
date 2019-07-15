package com.example.michael.doyouknowdeway;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hughman on 1/27/2018.
 * Tile Class for setting the structure and locations of blocks
 */

public class Tile {
    private int length, height;
    private Block[] blocks = new Block[5];
    private Block[][] tileMap;
    private ArrayList<Double> tidePods;
    private Random rand = new Random();

    //copy for use in later parts of code
    Tile(Tile copy)
    {
        this.length = copy.length;
        this.height = copy.height;
        this.blocks = copy.blocks;
        this.tileMap = copy.tileMap;
        this.tidePods = copy.tidePods;
    }

    //Creates the initial starting tile
    Tile(Context context, int number_block_types, int length, int height)
    {
        //create block classes
        for(int i = 0; i < number_block_types; i++) {
            blocks[i] = new Block(context, i);
        }
            tileMap = new Block[(length + 100)/100][height/100];
            this.height = height/100;
            this.length = (length + 100)/100;
    }

    //sets length and height of block (standardized)
    Tile(int length, int height, Block[] blocks)
    {
        rand = new Random();
        this.blocks = blocks;
        tileMap = new Block[length][height];
        this.height = height;
        this.length = length;
    }

    //fills the tile with blocks
    public void fillTile()
    {
        tidePods = new ArrayList<>();
        int random_height_cap = rand.nextInt(height - 4);
        int random_gap = rand.nextInt(length);
        int random_hill = rand.nextInt(length-4);

        if(random_height_cap == 0)
        {
            random_height_cap = 1;
        }
        if(random_hill <= 6)
        {
            random_hill = 13;
        }


        //add block to Tile, compliment to the fillTile(), additionally adds random gaps in the game
        //to present some level of difficulty
        boolean makehill = false;
        for(int i = 0; i < tileMap.length; i++)
        {
            int count = 0;
            int random_adjust = rand.nextInt(Math.abs(random_height_cap));
            int random_adjust2 = rand.nextInt(random_gap);

            if(i > 6 && i == random_gap || i == random_adjust)
            {
                continue;
            }

            if(makehill){count++;}

            for(int j = 0; j < tileMap[i].length; j++)
            {

                if(j == tileMap[i].length - 1)
                {
                    if(rand.nextBoolean() || rand.nextBoolean()) {
                        makeBlock(i, j, 0);
                    }
                    else{
                        if(rand.nextBoolean()) {
                            makeBlock(i, j, 1);
                        }
                    }
                }
                else{
                    if(i > 6)
                    {
                        if(i >= random_hill - random_adjust2 && i < random_hill - random_adjust + 3 && j >= tileMap[i].length + count - random_height_cap)
                        {
                            makeBlock(i, j, 0);
                            makehill = true;
                        }
                        else{
                            makehill = false;
                        }
                        if(i == random_hill + random_adjust - 2 && j >= tileMap[i].length - random_height_cap)
                        {
                            makeBlock(i, j, 0);
                        }
                        if(i == random_hill + random_adjust - 1 && j >= tileMap[i].length - random_height_cap - random_adjust)
                        {
                            makeBlock(i, j, 0);
                        }
                        if(i == random_hill + random_adjust && j >= tileMap[i].length - random_height_cap - random_adjust)
                        {
                            makeBlock(i, j, 0);
                        }
                    }
                    if(i == random_height_cap + random_adjust2 && j == tileMap[i].length - (random_adjust - 2))
                    {
                        makeBlock(i, j, 2);
                    }
                    if(i == random_adjust + 1 && j >= tileMap[i].length - 2)
                    {
                        makeBlock(i, j, 2);
                    }
                    if(i == random_gap - random_adjust2 && j == tileMap[i].length - random_adjust - 3)
                    {
                        makeBlock(i, j, 2);
                    }

                }
            }
        }
    }

    //makes the block to be added up above
    private void makeBlock(int i, int j, int k) {
        if(k == 2)
        {
            if(tileMap[i][j] == null) {
                tidePods.add((double) i + (j / 10.00));
                tileMap[i][j] = new Block(blocks[k], i, j);
            }
        }
        else {
            tileMap[i][j] = new Block(blocks[k], i, j);
        }
    }

    //getter methods
    int getHeight()
    {
        return height;
    }

    int getLength()
    {
        return length;
    }

    //to make the gaps in the blocks
    public void setNullBlock(int x, int y)
    {
        tileMap[x][y] = null;
        double compare = (double) x + (y/10.00);

        for(int i = 0; i < tidePods.size(); i++)
        {
            double comparitor = Math.abs(compare - tidePods.get(i));
            if(0 <= comparitor && comparitor < 1)
            {
                tidePods.remove(i);
                i--;
                return;
            }
        }
    }

    Block getBlock(int x, int y)
    {
        return tileMap[x][y];
    }

    Tile getNextTile()
    {
        Tile nextTile = new Tile(this);
        int randomizer = rand.nextInt(length/10);
        if(rand.nextBoolean())
        {
            nextTile = new Tile(length + randomizer, height, blocks);
        }
        else
        {
           if(length - randomizer <= 20)
           {
               randomizer = rand.nextInt(randomizer);
               nextTile = new Tile(length + randomizer, height, blocks);
           }
           else
           {
               nextTile = new Tile(length - randomizer, height, blocks);
           }
        }
        nextTile.fillTile();
        return nextTile;
    }

    ArrayList<Double> getTidePods()
    {
        return tidePods;
    }
}

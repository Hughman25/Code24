package com.example.michael.doyouknowdeway;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Hughman on 1/27/2018.
 */

public class Tile {
    private int length, height, ID;
    private Block[] blocks = new Block[5];
    private Block[][] tileMap;
    //private int[] possible_next_tile;
    private ArrayList<Double> tidePods;

    Tile(Tile copy)
    {
        this.length = copy.length;
        this.height = copy.height;
        this.blocks = copy.blocks;
        this.tileMap = copy.tileMap;
    }

    //Creates the initial starting tile
    Tile(Context context, int number_block_types, int length, int height)
    {
        ID = 0;
        //create block classes?
        for(int i = 0; i < number_block_types; i++) {
            // blocks[i] = new Block("grass")
            blocks[i] = new Block(context, i);
        }
            tileMap = new Block[(length + 100)/100][height/100];
            this.height = height/100;
            this.length = (length + 100)/100;
    }
    Tile(int length, int height, int ID, Block[] blocks)
    {
        this.ID = ID + 1;
        this.blocks = blocks;
        tileMap = new Block[length][height];
        this.height = height;
        this.length = length;
    }

    public void fillTile()
    {
        //add block to Tile
        for(int i = 0; i < tileMap.length; i++)
        {
            for(int j = 0; j < tileMap[i].length; j++)
            {
                if(j == tileMap[i].length - 1)
                {
                    if(i == 3) {
                        tileMap[i][j] = new Block(blocks[1], i, j);
                    }
                    else if(i == 9)
                    {
                        continue;
                    }
                    else {
                        tileMap[i][j] = new Block(blocks[0], i, j);
                    }
                }
                else if(j == tileMap[i].length - 2){
                     if(i == 5){
                        tileMap[i][j] = new Block(blocks[2], i, j);
                        tidePods.add((double)i + (j/10));
                    }
                }

                if(i == 8 && j >= tileMap[i].length - 3)
                {
                    tileMap[i][j] = new Block(blocks[0], i, j);
                }
                else if(i == 7 && j >= tileMap[i].length - 2)
                {
                    tileMap[i][j] = new Block(blocks[0], i, j);
                }
            }
        }
    }

    int getHeight()
    {
        return height;
    }

    int getLength()
    {
        return length;
    }

    public void setNullBlock(int x, int y)
    {
        tileMap[x][y] = null;
    }

    public int[] getBlockPosition(int x, int y)
    {
        return tileMap[x][y].getPosition();
    }

    Block getBlock(int x, int y)
    {
        return tileMap[x][y];
    }

    Tile getNextTile()
    {
        Tile nextTile = new Tile(length, height, ID, blocks);
        nextTile.fillTile();
        return nextTile;
    }

    ArrayList<Double> getTidePods()
    {
        return tidePods;
    }

    public int getID()
    {
        return ID;
    }

    int isEqualTo(Tile compare)
    {
        return ID - compare.getID();
    }
}

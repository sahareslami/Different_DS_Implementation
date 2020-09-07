// Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad, 2018 (1397 Hijri Shamsi)
//
// Author: Kamaledin Ghiasi-Shirazi

#pragma once

#include <iostream>
#include <string>
#include "InternalBinaryTreeNode.h"

using namespace std;

//template <class T, class IBTN = InternalBinaryTreeNode<T>>
template <class T,class IBTN>
class BinaryTree{
public:

    class BinaryTreeNode{
    public:
        virtual ~BinaryTreeNode(void){}
        virtual T&    getData(){return mActualNode->getData();}
        virtual bool    hasLeftChild() const{ return mActualNode->getLeftChild() != 0; }
        virtual BinaryTreeNode    getLeftChild()const { return BinaryTreeNode(mActualNode->getLeftChild()); }
        virtual bool    hasRightChild() const { return mActualNode->getRightChild() != 0; }
        virtual BinaryTreeNode    getRightChild() const { return BinaryTreeNode(mActualNode->getRightChild()); }

    private:
        template <class T,class R>
        friend class BinaryTree;
        BinaryTreeNode(IBTN* node){mActualNode = node;}

        IBTN    *mActualNode;
    };
    
protected:

    IBTN*     mDummyRoot;
    int        mNodeDisplayWidth;
    int        mSize;

public:
    BinaryTree(void){
        mNodeDisplayWidth = 2;
        mSize = 0;
        
        mDummyRoot   = new IBTN();
    }

    // caution: don't interpret virtual destrucor as an ordinary virtual function!
    virtual ~BinaryTree(void){
        DeleteSubtree (mDummyRoot);
    }

    int    size(){ return mSize; }
    void DeleteSubtree (IBTN* node){
        if (node->mLeftChild)
            DeleteSubtree (node->mLeftChild);
        if (node->mRightChild)
            DeleteSubtree (node->mRightChild);
        delete node;
    }

    bool    isEmpty(){
        return mDummyRoot->mLeftChild == 0;
    }

    virtual void    insertRootNode(const T& data){
        IBTN*    root;
        if (mDummyRoot->mLeftChild)
            throw ("Error: Root already exists.");
        root = new IBTN();
        root->mParent = mDummyRoot;
        *root->mData = data;
        root->mLeftChild = 0;
        root->mRightChild = 0;
        mDummyRoot->mLeftChild = root;
        mSize = 1;
    }

    virtual BinaryTreeNode    getRootNode(){return BinaryTreeNode(mDummyRoot->mLeftChild);}

    // error if a left child already exists.
    virtual void  insertLeftChild(const BinaryTreeNode& parentNode, const T& data){
        IBTN* newNode;
        if((parentNode.mActualNode)->mLeftChild)
            throw("ERROR: left child already exists");
        newNode = new IBTN();
        newNode->mParent = parentNode.mActualNode;
        *newNode->mData = data;
        newNode->mLeftChild = 0;
        newNode->mRightChild = 0;
        (parentNode.mActualNode)->mLeftChild = newNode;
    }

    // error if a right child already exists.
    virtual void insertRightChild(const BinaryTreeNode& parentNode, const T& data){
        IBTN* newNode;
        if((parentNode.mActualNode)->mRightChild)
            throw("ERROR : right child already exists");
        newNode = new IBTN;
        newNode->mParent = parentNode.mActualNode;
        *newNode->mData = data;
        newNode->mLeftChild = 0;
        newNode->mRightChild = 0;
        (parentNode.mActualNode)->mRightChild = newNode;
    }

    // Only leaf nodes and nodes with degree 1 can be deleted. 
    // If a degree-1 node is deleted, it is replaced by its subtree.
    virtual void  deleteNode(const BinaryTreeNode& node){

        //if node is leaf
		if((node.mActualNode)->mLeftChild == 0 && (node.mActualNode)->mRightChild == 0){
			//left child of his father
			if((node.mActualNode)->mParent->mLeftChild == (node.mActualNode)){
				(node.mActualNode)->mParent->mLeftChild = 0;
			}
			//right child of his father
			if((node.mActualNode)->mParent->mRightChild == (node.mActualNode))
				(node.mActualNode)->mParent->mRightChild = 0;
			
			delete node.mActualNode;
		}
        
        //if node is degree-1 and has left child
        else if((node.mActualNode)->mRightChild == 0 && (node.mActualNode)->mLeftChild != 0){
            //node is left child of his father
            if((node.mActualNode)->mParent->mLeftChild == (node.mActualNode))
				  (node.mActualNode)->mParent->mLeftChild = (node.mActualNode)->mLeftChild;
            //node is right child of his father
			else
					(node.mActualNode)->mParent->mRightChild = (node.mActualNode)->mLeftChild;
			(node.mActualNode)->mLeftChild = (node.mActualNode)->mParent;
			(node.mActualNode)->mLeftChild = 0;
			delete node.mActualNode;
        }
        //if node is degree-1 and has right child
        else if((node.mActualNode)->mRightChild != 0 && (node.mActualNode)->mLeftChild == 0){
            //node is left child of his father
			if((node.mActualNode)->mParent->mLeftChild == (node.mActualNode))
                (node.mActualNode)->mParent->mLeftChild = (node.mActualNode)->mRightChild;
				
            //node is right child of his father
			else
                (node.mActualNode)->mParent->mRightChild = (node.mActualNode)->mRightChild;

			(node.mActualNode)->mRightChild->mParent = (node.mActualNode)->mParent;
			(node.mActualNode)->mRightChild = 0;
			delete node.mActualNode;
        }
		else
			throw("ERROR : selected node has two child");
    }    

    void setNodeDisplayWidth(int width){
        mNodeDisplayWidth = width;
    }

    // This function is solely written to work on small binary trees.
    // The code has many other known limitations.
    void draw(std::ostream& out){
        int maxDepth = 9;
        int depth = depthCalc(getRootNode().mActualNode, 1);
        depth = depth * 2 - 1;

        if (depth > maxDepth){
            out << "Can't draw, the height of the tree is greater than " << (maxDepth + 1) / 2 << "\n";
            return;
        }

        char **map = new char *[depth];
        for (int i = 0; i < depth; i++){
            map[i] = new char[80];
            for (int j = 0; j < 80; j++)
                map[i][j] = 0;
        }

        recursiveDraw(getRootNode().mActualNode, map, 40, 0);

        for (int i = 0; i < depth; i++)
        for (int j = 0; j < 80; j++)
            out << map[i][j];

        for (int i = 0; i < depth; i++)
            delete[] map[i];
        delete[] map;
    }


    int depthCalc(IBTN* root, int depth){
        int res = depth;
        if (root->mRightChild){
            int rightDepth = depthCalc(root->mRightChild, depth + 1);
            res = (res > rightDepth) ? res : rightDepth;
        }
        if(root->mLeftChild){
            int leftDepth = depthCalc(root->mLeftChild, depth + 1);
            res = (res > leftDepth) ? res : leftDepth;
        }
        return res;
    }

    void recursiveDraw(IBTN* root, char** lines, int x, int y){
        int des = 1;
        for (int i = 0; i < y / 2 + 2; i++)
            des *= 2;
        des = 80 / des;
        //root:
        for (int i = 0; i < mNodeDisplayWidth; i++)
            lines[y][x + i - mNodeDisplayWidth / 2] = (*root->mData)[i];
        //left child:
        if (root->mLeftChild){
            for (int i = 0; i < des; i++)
                lines[y + 1][x - i] = '-';
            lines[y + 1][x] = '|';
            recursiveDraw(root->mLeftChild, lines, x - des, y + 2);
        }
        //right child:
        if (root->mRightChild){
            for (int i = 0; i < des; i++)
                lines[y + 1][x + i] = '-';
            lines[y + 1][x] = '|';
            recursiveDraw(root->mRightChild, lines, x + des, y + 2);
        }
    }

protected:
    virtual IBTN*    getActualNode(const BinaryTreeNode& node){ return node.mActualNode; }
};
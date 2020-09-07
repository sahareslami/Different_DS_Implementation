// Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad, 2018 (1397 Hijri Shamsi)
//
// Author: Kamaledin Ghiasi-Shirazi

#pragma once

template <class T, class IBTN>
class BinaryTree;

template <class T>
class InternalBinaryTreeNode
{
public:
    InternalBinaryTreeNode(void)
    {
        mLeftChild  = 0;
        mRightChild = 0;
        mParent        = 0;
        mData        = new T();
    }

    virtual ~InternalBinaryTreeNode(void)
    {
        delete mData;
    }

    virtual T&    getData()
    {
        return *mData;
    }

    virtual void setData(const T& data)
    {
        *mData = data;
    }

    virtual InternalBinaryTreeNode<T>*    getLeftChild()
    {
        return mLeftChild;
    }

    virtual InternalBinaryTreeNode<T>*    getRightChild()
    {
        return mRightChild;
    }

    template<typename S, typename R>
    friend class BinaryTree;

protected:
    InternalBinaryTreeNode<T>*    mParent;
    InternalBinaryTreeNode<T>*    mLeftChild;
    InternalBinaryTreeNode<T>*    mRightChild;
private:
    T*                            mData;
};
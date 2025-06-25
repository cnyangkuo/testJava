package com.interview.langzhi;

/**
 * 支持回退操作的队列, 支持 pop() 和 pop2() 操作的回退（类似撤销功能）
 * @author yangkuo
 * @date 2025/6/20
 * @description
 */
public class UndoableStackPipe<T> {
    private StackPipe<T> mainStack = new StackPipe<>();
    private StackPipe<T> undoStack = new StackPipe<>();
//    private StackPipe<T> undo2Stack = new StackPipe<>();

    public void push(T data) {
        mainStack.push(data);
    }

    public T popWithUndo() {
        T data = mainStack.pop();
        undoStack.push(data);
        return data;
    }

    public void undoLastPop() {
        if (!undoStack.isEmpty()) {
            mainStack.push(undoStack.pop());
        }
    }

//    public T pop2WithUndo() {
//        T data = mainStack.pop2();
//        undo2Stack.push(data);
//        return data;
//    }
//
//    public void undoLastPop2() {
//        if (!undo2Stack.isEmpty()) {
//            mainStack.push(undo2Stack.pop());
//        }
//    }

    public int size() {
        return mainStack.size();
    }

    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    public void printList(){
        mainStack.printList();
    }

    public static void main(String[] args) {
        UndoableStackPipe<Integer> stackPipe = new UndoableStackPipe<>();
        stackPipe.push(1);
        stackPipe.push(2);
        stackPipe.push(3);
        stackPipe.printList();

        stackPipe.popWithUndo();
        stackPipe.undoLastPop();
        stackPipe.printList();

//        stackPipe.pop2WithUndo();
//        stackPipe.undoLastPop2();
//        stackPipe.printList();

    }
}
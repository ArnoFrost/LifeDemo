// IBookStateListener.aidl
package com.arno.demo.life.binder;
import com.arno.demo.life.binder.bean.Book;

// Declare any non-default types here with import statements

interface IBookStateListener {
   void onNewBookArrived(in Book book);
}
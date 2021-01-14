// IBookManager.aidl
package com.arno.demo.life.binder;

// Declare any non-default types here with import statements

import com.arno.demo.life.binder.bean.Book;
import com.arno.demo.life.binder.IBookStateListener;

interface IBookManager {

   List<Book> getBookList();

   void addBook(in Book book);

   boolean addStateListener(in IBookStateListener listener);

   boolean removeStateListener(in IBookStateListener listener);
}
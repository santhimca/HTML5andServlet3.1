/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safeway.html5andservlet31;

import java.io.IOException;
import java.util.Queue;
import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 *
 * @author scher20
 */
public class WriteListenerImpl implements WriteListener{
    private ServletOutputStream output = null;
    private Queue queue = null;
    private AsyncContext context = null;

    public WriteListenerImpl(ServletOutputStream sos, Queue q, AsyncContext c) {
        output = sos;
        queue = q;
        context = c;
    }
    
    

    @Override
    public void onWritePossible() throws IOException {
        while(queue.peek() != null && output.isReady()) {
            String data = (String) queue.poll();
            output.print(data);
        }
        if (queue.peek() == null) {
            context.complete();
        }
    }

    @Override
    public void onError(Throwable t) {
        context.complete();
        t.printStackTrace();
    }
    
    
}

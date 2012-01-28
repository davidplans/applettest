/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package com.github.kayahr.applettest;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import netscape.javascript.JSObject;

/**
 * Test applet. It simply provides a text area and a button. The button sends
 * the entered text to JavaScript by calling the global "textChanged"
 * function. This is also done when the applet is starting up to give JavaScript
 * the initial text. JavaScript can also call the methods setText() and
 * getText() to set or retrieve the current text.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class AppletTest extends Applet
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The text area. */
    private JTextArea textArea;

    /**
     * @see java.applet.Applet#init()
     */
    @Override
    public void init()
    {
        setLayout(new BorderLayout());
        JTextArea textArea =
            this.textArea = new JTextArea(getParameter("text"));
        textArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(textArea, BorderLayout.CENTER);

        JButton button = new JButton("Send to JavaScript");
        add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendToJavaScript();
            }
        });
    }

    /**
     * @see java.applet.Applet#start()
     */
    @Override
    public void start()
    {
        sendToJavaScript();
    }

    /**
     * Sends the current text of the text field to JavaScript.
     */
    void sendToJavaScript()
    {
        JSObject win = JSObject.getWindow(this);
        win.call("textChanged", new Object[] { getText() });
    }

    /**
     * Sets the current text.
     * 
     * @param text
     *            The current text to set.
     */
    public void setText(String text)
    {
        this.textArea.setText(text);
    }

    /**
     * Returns the current text.
     * 
     * @return The current text.
     */
    public String getText()
    {
        return this.textArea.getText();
    }
}

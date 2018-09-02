/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.java.charles;

import javassist.*;

import java.io.IOException;

/**
 * @Author: Helixcs
 * @Time:9/2/18
 * Charles 2.6.4 cracked
 */
public class ASMPractice {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.insertClassPath("/Users/helix/IdeaProjects/MagicBag/api-tester/src/main/resources/charles.jar");

        CtClass ctClass  = classPool.getCtClass("com.xk72.charles.GPSz");
        CtMethod ctMethod = ctClass.getDeclaredMethod("Dgmx",null);
        ctMethod.setBody("{return true;}");

        ctMethod = ctClass.getDeclaredMethod("Wmmw",null);
        ctMethod.setBody("{return \"Helixcs\";}");
        ctClass.writeFile();

    }
}

package lwjgui;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL;

import lwjgui.scene.Context;

public class LWJGUIUtil {
	public static long createOpenGLCoreWindow(String name, int width, int height, boolean resizable, boolean ontop) {
		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		// Core OpenGL version 3.2
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW.GLFW_FLOATING, ontop?GL_TRUE:GL_FALSE);
		glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable?GL_TRUE:GL_FALSE);

		// Create the window
		long window = glfwCreateWindow(width, height, name, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Finalize window
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		
		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		// Center the window
		GLFW.glfwSetWindowPos(
			window,
			(vidmode.width() - width) / 2,
			(vidmode.height() - height) / 2
		);
		
		// Create context
		GL.createCapabilities();
		
		return window;
	}

	public static void fillRect(Context context, double x, double y, double width, double height, Color color) {
		if ( color == null )
			return;
		
		NanoVG.nvgBeginPath(context.getNVG());
		NanoVG.nvgRect(context.getNVG(), (int)x, (int)y, (int)width, (int)height);
		NanoVG.nvgFillColor(context.getNVG(), color.getNVG());
		NanoVG.nvgFill(context.getNVG());
	}

	public static void fillRoundRect(Context context, double x, double y, double width, double height, double radius, Color color) {
		if ( color == null )
			return;
		
		NanoVG.nvgBeginPath(context.getNVG());
		NanoVG.nvgRoundedRect(context.getNVG(), (int)x, (int)y, (int)width, (int)height, (float)radius);
		NanoVG.nvgFillColor(context.getNVG(), color.getNVG());
		NanoVG.nvgFill(context.getNVG());
	}

	public static void outlineRect(Context context, double x, double y, double w, double h, Color color) {
		if ( color == null )
			return;
		
		x = (int)x;
		y = (int)y;
		w = (int)w;
		h = (int)h;
		fillRect( context, x, y, w, 1, color );
		fillRect( context, x, y+h, w+1, 1, color );
		fillRect( context, x, y, 1, h, color );
		fillRect( context, x+w, y, 1, h, color );
	}
}
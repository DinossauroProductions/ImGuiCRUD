package com.dinossauroProductions.renderer;

import com.dinossauroProductions.util.Config;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    //tamanho da janela
    private static Vector2i windowSize = Config.windowSize;

    public static ImGuiIO io;

    private String glslVersion = null;

    private long windowPtr;

    private Scene currentScene;

    private String fontPath = "src/main/resources/Fonts/Noto-Sans/NotoSans-Medium.ttf";

    public Window(Scene scene){
        currentScene = scene;

    }

    public Window(){

    }

    public void init(){

        initWindow();
        initImGui();
        //System.out.println(windowPtr);
        imGuiGlfw.init(windowPtr, true);
        imGuiGl3.init(glslVersion);
    }

    public void destroy(){
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
        Callbacks.glfwFreeCallbacks(windowPtr);
        glfwDestroyWindow(windowPtr);
        glfwTerminate();
    }

    public void initWindow(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            System.out.println("Unable to initialize GLFW");
            System.exit(-1);

        }

        //setting versions
        glslVersion = "#version 460";
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);

        //setting window stuff

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        windowPtr = glfwCreateWindow(windowSize.x, windowSize.y, "Janela", NULL, NULL);

        if (windowPtr == NULL) {
            //poorly handled error but whatever
            System.out.println("Unable to create window");
            System.exit(-1);
        }

        GLFW.glfwMaximizeWindow(windowPtr);

        glfwMakeContextCurrent(windowPtr);
        glfwSwapInterval(1);
        glfwShowWindow(windowPtr);

        GL.createCapabilities();

    }

    public void initImGui(){
        ImGui.createContext();

        io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);

        //io.getFonts().addFontFromFileTTF(fontPath, 16, io.getFonts().getGlyphRangesDefault());

    }

    public void run(){
        while(!glfwWindowShouldClose(windowPtr)){
            //clear screen
            glClearColor(0.1f, 0.09f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            //initialize the current frame
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            //start frame code
            if (io.hasConfigFlags(ImGuiConfigFlags.DockingEnable)) {
                ImGuiViewport viewport = ImGui.getMainViewport();
                ImGui.setNextWindowPos(viewport.getWorkPosX(), viewport.getWorkPosY());
                ImGui.setNextWindowSize(viewport.getWorkSizeX(), viewport.getWorkSizeY());
                ImGui.setNextWindowViewport(viewport.getID());
                ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
                ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
                int windowFlags = ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
                ImGui.begin("DockSpace Demo", windowFlags);
                ImGui.popStyleVar(2);

                // Configura o docking
                ImGui.dockSpace(ImGui.getID("MyDockSpace"));

            }

            ImGui.end();

            currentScene.renderScene();


            //end frame code


            //render what was done
            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());

            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                final long backupWindowPtr = GLFW.glfwGetCurrentContext();
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
                GLFW.glfwMakeContextCurrent(backupWindowPtr);
            }

            GLFW.glfwSwapBuffers(windowPtr);
            GLFW.glfwPollEvents();
        }
    }

    public static int getWidth(){
        return windowSize.x;
    }

    public static int getHeight(){
        return windowSize.y;
    }

    public long getId(){
        return windowPtr;
    }

    public Scene getScene(){
        return currentScene;
    }

    public void setScene(Scene scene){
        this.currentScene = scene;
    }
}


1;

pkg load signal;
clear;

%fs = 10000;
%t = 0:1/fs:1.5;
%x1 = 5.0 * sawtooth(2*pi*50*t);
% x2 = square(2*pi*50*t);
%subplot(211);plot(t,x1);axis([0 0.2 -0.5 6.0]);
%xlabel('Time (sec)');ylabel('Amplitude');title('Input Signal');

## UI style constants
global panelBackground = [0.2, 0.2, 0.2]
global panelForeground = [0.8, 0.8, 0.8]
global controlBackground = [0.25, 0.25, 0.25]
global controlForeground = [0.8, 0.8, 0.8]
global elementSize = [180, 20]

## Input variables
global inputPulseWidth = 4.0
global inputVoltage = 12.0

function label(parent, name, position)

  global controlBackground
  global controlForeground
  global elementSize

  uicontrol(parent, 
          "style", "text", 
          "string", name, 
          "position", [position, elementSize], 
          "backgroundcolor", controlBackground,
          "foregroundcolor", controlForeground)
endfunction

function slider(parent, position, min, max, value, callback)
   global elementSize
   uicontrol(parent,
            "style", "slider", 
            "position", [position, elementSize],
            "min", min,
            "max", max,
            "value", value,
            "callback", {callback})
endfunction

function simulate()  
  global inputPulseWidth
  global inputVoltage
  printf("Input pulse width, mS: %.3f\n", inputPulseWidth)
  printf("Input voltage, V : %.3f\n", inputVoltage)
endfunction  

function app() 

  global panelBackground
  global panelForeground
  global controlBackground
  global controlForeground

  mainWnd = figure("name", "Pulse width compensation", 
                  "position", [300,200, 800,600])
  
  # Controls for input values
  vPanel = uipanel(mainWnd, 
                  "title", "Input pulse width & voltage", 
                  "position", [0.01,0.55, 0.25, 0.40], 
                  "backgroundcolor", panelBackground,
                  "foregroundcolor", panelForeground)

  ## Original pulse width of the input signal  
  label(vPanel, "Input Signal Pulse Width", [5, 190])
  slider(vPanel, [5, 160], 4.0, 12.2, 4.0, @getInputPulseWidth)

  # input voltage (V+ power supply rail) 
  label(vPanel, "Input Voltage", [5, 110])
  slider(vPanel, [5, 80], 4.0, 12.2, 4.0, @getInputVoltage)

endfunction

function getInputPulseWidth(handle, event)
  global inputPulseWidth
  inputPulseWidth = get(handle, 'value')
  simulate()
endfunction

function getInputVoltage(handle, event)
  global inputVoltage
  inputVoltage = get(handle, 'value')
  simulate()
endfunction

app;

## As described in TI SLOA097 application note
1;

## UI style constants
global panelBackground = [1.0, 1.0, 0.8];
global panelForeground = [0.0, 0.0, 0.0];
global controlBackground = [1.0, 1.0, 0.8];
global controlForeground = [0.0, 0.0, 0.0];

global inputSize = [80,20]
global xOffset = 10;
global yOffset = 10;

## Create a pair of label and input field
function field = inputField(parent, name, position)
  
  global controlBackground
  global controlForeground
  global inputSize
  global xOffset
  global yOffset
  
  uicontrol(parent, "style", "text", "string", name, 
                          "position", [position, inputSize], 
                          "backgroundcolor", controlBackground,
                          "foregroundcolor", controlForeground);
                          
  field = uicontrol(parent, "style", "edit", "string", "0.0", 
  "position", [position(1) + inputSize(1) + xOffset, position(2), inputSize], 
  "backgroundcolor", controlBackground,
  "foregroundcolor", controlForeground)
endfunction

function inputValues = updateInput(h,e,inputs)
  inputValues.vInMin = get(inputs.vInMinInput, 'string')
  inputValues.vInMax = get(inputs.vInMaxInput, 'string')
  inputValues.vOutMin = get(inputs.vOutMinInput, 'string')
  inputValues.vOutMax = get(inputs.vOutMaxInput, 'string')
  disp(inputValues)
endfunction  

function [m,b] = mb(Vin_min, Vin_max, Vout_min, Vout_max)
  m = (Vout_max - Vout_min) / (Vin_max - Vin_min)
  b = Vout_min - m * Vin_min
endfunction

# Main entry point
function calculateValues(Vin_min, Vin_max, Vout_min, Vout_max) 
  #Vin_min = input("Pick Vin_min: ")
  #Vin_max = input("Pick Vin_max: ")
  #Vout_min = input("Pick Vout_min: ")
  #Vout_max = input("Pick Vout_max: ")
  
  # calculate slope and intercept
  [m,b] = mb(Vin_min, Vin_max, Vout_min, Vout_max)
  
  if(m > 0.0 && b > 0.0) 
     printf("case 1: positive m, positive b\n")
  elseif(m > 0.0 && b < 0.0)
    printf("case 2: positive m, negative b\n")
  elseif(m < 0.0 && b > 0.0)
    printf("case 3: negative m, positive b\n")
  else # m and b both < 0.0
    printf("case 4: negative m, negative b\n")
  endif
   
endfunction



function app()  
  global panelBackground
  global panelForeground
  global controlBackground
  global controlForeground
  
  f = figure("name", "Gain / dc offset calculator", "position", [300,200, 800,600])
 
  # Voltages
  #VinMin = 0.0; VinMax = 0.0; VoutMin = 0.0; VoutMax = 0.0;
  
  vPanel = uipanel(f, "title", "In/Out voltage ranges", 
                   "position", [0.01,0.70, 0.25, 0.25], 
                   "backgroundcolor", panelBackground,
                   "foregroundcolor", panelForeground);
         
  inputs.vInMinInput = inputField(vPanel, "Vin_min", [10,100]);
  inputs.vInMaxInput = inputField(vPanel, "Vin_max", [10,70]);
  inputs.vOutMinInput = inputField(vPanel, "Vout_min", [10,40]);
  inputs.vOutMaxInput = inputField(vPanel, "Vout_max", [10,10]);
 
  okButton = uicontrol(f, "string", "calculate", "position", [10, 130, 80, 20], "callback", {@updateInput, inputs});
 
endfunction

app;
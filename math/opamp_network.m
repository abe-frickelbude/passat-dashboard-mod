
## As described in TI SLOA097 application note
1;

## UI style constants
global panelBackground = [1.0, 1.0, 0.8];
global panelForeground = [0.0, 0.0, 0.0];
global controlBackground = [1.0, 1.0, 0.8];
global controlForeground = [0.0, 0.0, 0.0];

global inputSize = [100,20]
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

# Update values from all input boxes
function inputValues = updateInput(h,e,inputs)
  
  inputValues.vInMin = strread(get(inputs.vInMinInput, 'string'), "%f");
  inputValues.vInMax = strread(get(inputs.vInMaxInput, 'string'), "%f");
  inputValues.vOutMin = strread(get(inputs.vOutMinInput, 'string'), "%f");
  inputValues.vOutMax = strread(get(inputs.vOutMaxInput, 'string'), "%f");
  inputValues.vRef = strread(get(inputs.vRefInput, 'string'), "%f");
  inputValues.R1 = strread(get(inputs.r1Input, 'string'), "%f");
  inputValues.Rf = strread(get(inputs.rfInput, 'string'), "%f");
  
endfunction  

function updateValues(h,e, inputs) 
  inputValues = updateInput(h,e,inputs) 
  calculateValues(inputValues)
  # output values
  
endfunction  

function [m,b] = mb(Vin_min, Vin_max, Vout_min, Vout_max)
  
  m = (Vout_max - Vout_min) / (Vin_max - Vin_min)
  b = Vout_min - m * Vin_min
  
endfunction

function solveCase2(R1, Rf, Vref, m, b)
  
  Rg = Rf / (m - 1.0)
  Rg2 = Rg / 10.0
  Rg1 = Rg - Rg2
  Vref2 = (abs(b) * Rg1) / (Rg1 + Rf)
  R1 = Rg2 * (Vref - Vref2) / Vref2
  
endfunction

# Main entry point
function calculateValues(inputs) 
    
  # calculate slope and intercept
  [m,b] = mb(inputs.vInMin, inputs.vInMax, inputs.vOutMin, inputs.vOutMax)
  
  if(m > 0.0 && b > 0.0) 
     printf("case 1: positive m, positive b\n")
  elseif(m > 0.0 && b < 0.0)
    printf("case 2: positive m, negative b\n")
    solveCase2(inputs.R1, inputs.Rf, inputs.vRef, m, b)
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
  vPanel = uipanel(f, "title", "In/Out voltage ranges", 
                   "position", [0.01,0.65, 0.25, 0.30], 
                   "backgroundcolor", panelBackground,
                   "foregroundcolor", panelForeground);
         
  inputs.vInMinInput = inputField(vPanel, "Vin_min", [10,130]);
  inputs.vInMaxInput = inputField(vPanel, "Vin_max", [10,100]);
  inputs.vOutMinInput = inputField(vPanel, "Vout_min", [10,70]);
  inputs.vOutMaxInput = inputField(vPanel, "Vout_max", [10,40]);
  inputs.vRefInput = inputField(vPanel, "Vref", [10,10]);
 
  # Resistances
  rPanel = uipanel(f, "title", "Resistances", 
                   "position", [0.01,0.32, 0.25, 0.25], 
                   "backgroundcolor", panelBackground,
                   "foregroundcolor", panelForeground);
  
  inputs.r1Input = inputField(rPanel, "R1", [10, 100]);
  inputs.rfInput = inputField(rPanel, "Rf", [10, 70]);
  
  # Actions
  okButton = uicontrol(f,
                      "style", "pushbutton", 
                      "string", "calculate", 
                      "position", [10, 130, 150, 40], 
                      "backgroundcolor", controlBackground,
                      "foregroundcolor", controlForeground,
                      "callback", {@updateValues, inputs});
 
endfunction

app;
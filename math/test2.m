
pkg load signal;
clear;

function v = pwms(t,period,duty) 
  pw = period*duty; 
  v = pulstran(t,(period-pw/2):period:length(t),'rectpuls',period*duty); 
  plot(v)
endfunction 

pwms(0:0.001:1, 0012.5, 0.5)


%fs = 100;                   # arbitrary sample rate
%f0 = 100;                     # pulse train sample rate
%w = 0.004;                    # pulse width of 3 milliseconds
%t = 0:1/fs:0.1; d=0:1/f0:0.1; # define sample times and pulse times
%a = hanning(length(d));       # define pulse amplitudes

%fw = boxcar(10)
%x = pulstran() 
 
%x = pulstran(t', d', 'rectpuls', w)
%plot([0:length(x)-1]*1000/fs, x)

%  %subplot(221);
%  x = pulstran(t', d', 'rectpuls', w);
%  plot([0:length(x)-1]*1000/fs, x);
%  hold on; plot(d*1000,ones(size(d)),'g*;pulse;'); hold off;
%  ylabel("amplitude"); xlabel("time (ms)");
%  title("rectpuls");

%  %subplot(223);
%  x = pulstran(f0*t, [f0*d', a], 'sinc');
%  plot([0:length(x)-1]*1000/fs, x);
%  hold on; plot(d*1000,a,'g*;pulse;'); hold off;
%  ylabel("amplitude"); xlabel("time (ms)");
%  title("sinc => band limited interpolation");

%  %subplot(222);
%  pulse = boxcar(30);  # pulse width of 3 ms at 10 kHz
%  x = pulstran(t, d', pulse, 10000);
%  plot([0:length(x)-1]*1000/fs, x);
%  hold on; plot(d*1000,ones(size(d)),'g*;pulse;'); hold off;
%  ylabel("amplitude"); xlabel("time (ms)");
%  title("interpolated boxcar");

%  %subplot(224);
%  pulse = sin(2*pi*[0:0.0001:w]/w).*[w:-0.0001:0];
%  x = pulstran(t', [d', a], pulse', 10000);
%  plot([0:length(x)-1]*1000/fs, x);
%  hold on; plot(d*1000,a*w,'g*;pulse;'); hold off; title("");
%  ylabel("amplitude"); xlabel("time (ms)");
%  title("interpolated asymmetric sin");

 %----------------------------------------------------------
 % Should see (1) rectangular pulses centered on *,
 %            (2) rectangular pulses to the right of *,
 %            (3) smooth interpolation between the *'s, and
 %            (4) asymmetric sines to the right of *
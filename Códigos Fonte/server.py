from __future__ import print_function
from flask import Flask
import os, sys, subprocess, time


#setando uma porta pra cada efeito    
def sendAudio(message=''):
    os.system("echo '" + message + "' | pdsend 3000")

def sendChorus(message=''):
    os.system("echo '" + message + "' | pdsend 3001")

def sendFuzz(message=''):
    os.system("echo '" + message + "' | pdsend 3002")

def sendDelay(message=''):
    os.system("echo '" + message + "' | pdsend 3003")

def sendWahwah(message=''):
    os.system("echo '" + message + "' | pdsend 3004")

def audioOn():
    message = '0 1;'
    sendAudio(message)

def initialize():
    #abre os efeitos
    subprocess.call(["xdg-open","audioon.pd"])
    time.sleep(3)
    subprocess.call(["xdg-open","chorus.pd"])
    time.sleep(3)
    subprocess.call(["xdg-open","fuzz.pd"])
    time.sleep(3)
    subprocess.call(["xdg-open","delay.pd"])
    time.sleep(3)
    subprocess.call(["xdg-open","wahwah.pd"])
    #espera os efeitos carregarem
    time.sleep(5)
    #liga o audio
    audioOn()
    #deixa os efeitos em off
    message = '3 0;'
    sendChorus(message)
    message = '0 0;'
    sendFuzz(message)
    message = '0 0;'
    sendDelay(message)
    message = '3 0;'
    sendWahwah(message)

    
initialize()



app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

@app.route('/gemfx/effects/<effect>/<status>')
def status(effect, status):
    if effect == 'distortion':
        if status == 'on':
            #codigo distortion on
			sendFuzz(message)
			message = '0 1;'
            return "distortion on"
        elif status == 'off':
            #codigo distortion off
			sendFuzz(message)
			message = '0 0;'
            return "distortion off"
        else:
            return "valor invalido"

    elif effect == 'delay':
        if status == 'on':
            #codigo delay on
			sendDelay(message)
			message = '0 1;'
            return "delay on"
        elif status == 'off':
            #codigo delay off
			sendDelay(message)
			message = '0 0;'
            return "delay off"
        else:
            return "valor invalido"

    elif effect == 'chorus':
        if status == 'on':
            #codigo chorus on
			sendChorus(message)
			message = '3 1;'
            return "chorus on"
        elif status == 'off':
            #codigo chorus off
			sendChorus(message)
			message = '3 0;'
            return "chorus off"
        else:
            return "valor invalido"
        
    elif effect == 'gemeffect':
        if status == 'on':
            #codigo gemeffect on
			sendWahwah(message)
			message = '3 1;'
            return "gemeffect on"
        elif status == 'off':
            #codigo gemeffect off
			sendWahwah(message)
			message = '3 0;'
            return "gemeffect off"
        else:
            return "valor invalido"

    else:
        "efeito invalido"

@app.route('/gemfx/amplitude/<int:value>')
def amplitude(value):
    #codigo p/ mudar a amplitude da onde
    return "amplitude valor {}".format(value)

@app.route('/gemfx/settings/<effect>/<setting>/<int:value>')
def settings(effect, setting, value):
    if effect == 'distortion':
        if setting == 'level':
            #codigo p/ level distortion
			message = '0 ' + str(value) + ';'
			sendFuzz(message)
            return "distortion level {}".format(value)
        else:
            return "setting invalido"
    
    elif effect == 'chorus':
        if setting == 'depth':
            #codigo p/ depth chorus
			message = '0 ' + str(value) + ';'
			sendChorus(message)
            return "chorus level {}".format(value)
        elif setting == "rate":
            #codigo p/ rate chorus
			message = '1 ' + str(value) + ';'
			sendChorus(message)
            return "chorus rate {}".format(value)
        else:
            return "setting invalido"

    elif effect == 'delay':
        if setting == 'level':
            #codigo p/ level delay
			message = '1 ' + str(value) + ';'
			sendDelay(message)
            return "delay level {}".format(value)
        elif setting == 'duration':
            #codigo p/ duration delay
			message = '2 ' + str(value) + ';'
			sendDelay(message)
            return "delay duration {}".format(value)
        else:
            return "setting invalido"

    elif effect == 'gemeffect':
        if setting == 'low':
            #codigo p/ low gem effect
			message = '0 ' + str(value) + ';'
			sendWahwah(message)
            return "gemeffect low {}".format(value)
        elif setting == 'mid':
            #codigo p/ mid gem effect
			message = '1 ' + str(value) + ';'
			sendWahwah(message)
            return "gemeffect mid {}".format(value)
        elif setting == 'high':
            #codigo p/ high gem effect
			message = '1 ' + str(value) + ';'
			sendWahwah(message)
            return "gemeffect high {}".format(value)
        elif setting == 'wahwah':
            #codigo p/ wahwah gem effect
			message = '2 ' + str(value) + ';'
			sendWahwah(message)
            return "gemeffect wahwah {}".format(value)
    
    else:
        return "efeito invalido"


#deixar host como 0.0.0.0 p aceitar conexoes externas
if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=False)



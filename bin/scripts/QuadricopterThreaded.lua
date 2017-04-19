
-- Seta forca (recebida por parametro) aos motores do quadricopter 
function setThrusts(inInts,inFloats,inStrings,inBuffer)
    thrusts[1] = inFloats[1]
    thrusts[2] = inFloats[2]
    thrusts[3] = inFloats[3]
    thrusts[4] = inFloats[4]
    return {},{thrusts[1], thrusts[2], thrusts[3], thrusts[4]},{},''
end
-- Retorna a forca atual de cada um dos motores do quadricopter
function getThrusts(inInts,inFloats,inStrings,inBuffer)
    return {},{thrusts[1], thrusts[2], thrusts[3], thrusts[4]},{},''
end

function scalarTo3D(s, a)
    return {s*a[3], s*a[7], s*a[11]}
end

function rotate(x, y, theta)
    return {math.cos(theta)*x + math.sin(theta)*y, -math.sin(theta)*x + math.cos(theta)*y}
end

if (sim_call_type==sim_childscriptcall_initialization) then 
    -- forca inicial dos motores
    thrusts = {0.0, 0.0, 0.0, 0.0}

    base = simGetObjectHandle('Quadricopter_base')

    propellerList = {}
    propellerRespondableList = {}

    -- PEGA REFERENCIA AOS MOTORES DO QUADRICOPTER 
    for i = 1, 4, 1 do
        propellerList[i]=simGetObjectHandle('Quadricopter_propeller'..i)
        propellerRespondableList[i]=simGetObjectHandle('Quadricopter_propeller_respondable'..i)

    end

    particleCountPerSecond = 430 --simGetScriptSimulationParameter(sim_handle_self,'particleCountPerSecond')
    particleDensity = 8500 --simGetScriptSimulationParameter(sim_handle_self,'particleDensity')

    baseParticleSize = 1 --simGetScriptSimulationParameter(sim_handle_self,'particleSize')
    timestep = simGetSimulationTimeStep()

    -- Variavel para armazenar a quantidade de particulas
    particleSizes = {}


    for i = 1, 4, 1 do

        propellerSizeFactor = simGetObjectSizeFactor(propellerList[i])
        particleSizes[i] = baseParticleSize*0.005*propellerSizeFactor
    end

    particleCount = math.floor(particleCountPerSecond * timestep)
    frontCam=simGetObjectHandle('Front_camera')
    frontView=simFloatingViewAdd(0.7,0.9,0.2,0.2,0)
    simAdjustView(frontView,frontCam,64)
end


if (sim_call_type==sim_childscriptcall_actuation) then
--Ira calcular e setar a forca e o sentido de cada motor
    for i = 1, 4, 1 do
        thrust = thrusts[i]
        force = particleCount * particleDensity * thrust * math.pi * math.pow(particleSizes[i],3) / (6 * timestep)
        torque = math.pow(-1, i+1)*.002 * thrust

        -- Seta as helices
        simSetFloatSignal('Quadricopter_propeller_respondable'..i, propellerRespondableList[i])

        propellerMatrix = simGetObjectMatrix(propellerList[i],-1)

        forces = scalarTo3D(force,  propellerMatrix)
        torques = scalarTo3D(torque, propellerMatrix)

        -- Seta a forca e o sentido do motor
        for k = 1, 3, 1 do
            simSetFloatSignal('force'..i..k,  forces[k])
            simSetFloatSignal('torque'..i..k, torques[k])
        end
    end
end


if (sim_call_type==sim_childscriptcall_sensing) then

end


if (sim_call_type==sim_childscriptcall_cleanup) then
    simFloatingViewRemove(frontView)
end
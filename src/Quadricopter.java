import coppelia.FloatWA;
import coppelia.IntW;
import coppelia.remoteApi;

public class Quadricopter 
{
//	public static void main(String[] args) throws InterruptedException
//	{
//		System.out.println("Program started");
//        remoteApi vrep = new remoteApi();
//        vrep.simxFinish(-1); // Se for o caso, fecha todas as conexões abetas
//        //porta,time e thread
//        int clientID = vrep.simxStart("127.0.0.1",19999,true,true,5000,5);
//        if (clientID!=-1)
//        {
//            System.out.println("Connected to remote API server");
//            
//			vrep.simxAddStatusbarMessage(clientID,"Olá!",remoteApi.simx_opmode_oneshot);
//            
//			// criando o dicionario de dados
//            IntW q1Handle = new IntW(0);
//            
//            // Pega a referencia ao Quadricopter Q1
//            vrep.simxGetObjectHandle(clientID, "Q1", q1Handle, vrep.simx_opmode_blocking);
//            System.out.println("Q1 handle: " + q1Handle.getValue());
//            FloatWA currentThrusts = new FloatWA(4);
//            // Pega a força dos 4 motores do Quadricopter Q1 através
//            //de chamada ao metodo 'geThrusts' ddo ChildScript LUA
//            int result = vrep.simxCallScriptFunction(clientID,"Q1",vrep.sim_scripttype_childscript,"getThrusts",null,null,null,null,null,currentThrusts,null,null,vrep.simx_opmode_blocking);
//
//            // Faz 1000 interaçoes aumentando a forca de cada motor em 0.01f.
//            for(int i = 0; i < 550; i++){
//                FloatWA newThrusts=new FloatWA(4);
//                // Aumenta a forca de cada motor em 0.01f
//                for(int index = 0; index < 4; index++){
//                    newThrusts.getArray()[index] = currentThrusts.getArray()[index] + 0.01f;
//                }
//
//                // Envia ao Q1 a forca atualizada de cada motor, atraves 
//                //de chamada ao metodo 'setThrusts' do childScript Lua	
//                result = vrep.simxCallScriptFunction(clientID,"Q1",vrep.sim_scripttype_childscript,"setThrusts",null,newThrusts,null,null,null,currentThrusts,null,null,vrep.simx_opmode_blocking);
//                if (result==vrep.simx_return_ok) // Se o envio da forca atualizada ao 
//                	//Q1 ocorrer com sucesso,
//                	//imprime no console a forca de cada motor atualizada
//                    System.out.format(String.format("\nCurrent thrusts: %s %s %s %s",
//                            currentThrusts.getArray()[0],
//                            currentThrusts.getArray()[1],
//                            currentThrusts.getArray()[2],
//                            currentThrusts.getArray()[3]));
//                else
//                	// se nao, imprime mensagem de falha
//                    System.out.format("Remote function call failed\n");
//                Thread.sleep(10);
//            }
//
//
//            vrep.simxFinish(clientID); // finaliza a conexao
//        }
//        else
//            System.out.println("Failed connecting to remote API server");
//        System.out.println("Program ended");
//	}
	
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("Program started");
        remoteApi vrep = new remoteApi();
        vrep.simxFinish(-1); // Se for o caso, fecha todas as conexões abetas
        //porta,time e thread
        int clientID = vrep.simxStart("127.0.0.1",19999,true,true,5000,5);
        if (clientID!=-1)
        {
            System.out.println("Connected to remote API server");
            
			vrep.simxAddStatusbarMessage(clientID,"Olá!",remoteApi.simx_opmode_oneshot);
            
			// criando o dicionario de dados
            IntW q1Handle = new IntW(0);
            
            // Pega a referencia ao Quadricopter Q1
            vrep.simxGetObjectHandle(clientID, "Q1", q1Handle, vrep.simx_opmode_blocking);
//            System.out.println("Q1 handle: " + q1Handle.getValue());
            // Variável que armazenará os valores lidos da força dos 4 motores
            FloatWA currentThrusts = new FloatWA(4);
            // Variável que armazenará os valores lidos do Acelerômetro
            FloatWA currentAccel = new FloatWA(3);
            // Variável que armazenará os valores lidos do Gyrosensor
            FloatWA currentGyro = new FloatWA(3);
            // Pega a força dos 4 motores do Quadricopter Q1 através
            //de chamada ao metodo 'getThrusts' do ChildScript LUA
            int result = vrep.simxCallScriptFunction(clientID,"Q1",vrep.sim_scripttype_childscript,"getThrusts",null,null,null,null,null,currentThrusts,null,null,vrep.simx_opmode_blocking);
            // Pega o valor lido do Acelerômetro do Quadricopter Q1 através
            //de chamada ao metodo 'getAccel' do ChildScript LUA
            int resultAc = vrep.simxCallScriptFunction(clientID,"Accelerometer",vrep.sim_scripttype_childscript,"getAccel",null,null,null,null,null,currentAccel,null,null,vrep.simx_opmode_blocking);
            // Pega o valor lido do Gyrosensor do Quadricopter Q1 através
            //de chamada ao metodo 'getGyro' do ChildScript LUA
            int resultGy = vrep.simxCallScriptFunction(clientID,"GyroSensor",vrep.sim_scripttype_childscript,"getGyro",null,null,null,null,null,currentGyro,null,null,vrep.simx_opmode_blocking);

            //imprime no console a forca de cada motor atualizada
            System.out.format(String.format("\nCurrent thrusts: %s %s %s %s",
                    currentThrusts.getArray()[0],
                    currentThrusts.getArray()[1],
                    currentThrusts.getArray()[2],
                    currentThrusts.getArray()[3]));
            
            //imprime no console os valores de X, Y e Z lidos no Acelerômetro
            System.out.format(String.format("\nCurrent accelerometer: %.4f %.4f %.4f",
                    currentAccel.getArray()[0],
                    currentAccel.getArray()[1],
                    currentAccel.getArray()[2]));
            
            //imprime no console os valores de X, Y e Z lidos no Gyrosensor
            System.out.format(String.format("\nCurrent gyrosensor: %.4f %.4f %.4f",
                    currentGyro.getArray()[0],
                    currentGyro.getArray()[1],
                    currentGyro.getArray()[2]));


            vrep.simxFinish(clientID); // finaliza a conexao
        }
        else
            System.out.println("Failed connecting to remote API server");
        System.out.println("\nProgram ended");
	}
}

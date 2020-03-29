package com.gojek.parking;

import com.gojek.parking.commands.CommandLineProcessor;

import java.io.*;

public class ParkingMain
{

    public static void main( String... args){
        CommandLineProcessor processor = new CommandLineProcessor();

        BufferedReader bufferReader = null;
        String input = null;
        try
        {
            System.out.println("\n\n\n\n\n");
            System.out.println("Parking Lot For GoJek");

            printUsage();
            System.out.println( args.length );
            switch (args.length)
            {
                case 0: // Interactive: command-line input/output
                {
                    System.out.println("Please Enter 'exit' to end Execution");
                    System.out.println("Input:");
                    while (true)
                    {
                        try
                        {
                            bufferReader = new BufferedReader(new InputStreamReader(System.in));
                            input = bufferReader.readLine().trim();
                            if (input.equalsIgnoreCase("exit"))
                            {
                                break;
                            }
                            else
                            {
                                    try
                                    {
                                        processor.parse(input.trim());
                                    }
                                    catch (Exception e)
                                    {
                                        System.out.println(e.getMessage());
                                    }

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 1:// File input/output
                {
                    System.out.println( args [0]);
                    File inputFile = new File(args[0]);
                    try
                    {
                        bufferReader = new BufferedReader(new FileReader(inputFile));
                        while ((input = bufferReader.readLine()) != null)
                        {
                            input = input.trim();
                                try
                                {
                                    processor.parse(input);
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e.getMessage());
                                }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                if (bufferReader != null)
                    bufferReader.close();
            }
            catch ( IOException e)
            {
            }
        }
    }

    private static void printUsage()
    {
        StringBuffer buffer = new StringBuffer();
        buffer = buffer.append(
                "Please Enter one of the below commands")
                .append("\n");
        buffer = buffer.append("create_parking_lot <capacity>")
                .append("\n");
        buffer = buffer
                .append("park <registration_no> <color>")
                .append("\n");
        buffer = buffer.append("leave <slot_no>")
                .append("\n");
        buffer = buffer.append("status").append("\n");
        buffer = buffer.append(
                "registration_numbers_for_cars_with_color <color>")
                .append("\n");
        buffer = buffer.append(
                "slot_numbers_for_cars_with_color <color>")
                .append("\n");
        buffer = buffer.append(
                " slot_number_for_registration_number <registration_no>")
                .append("\n");
        System.out.println(buffer.toString());
    }

}

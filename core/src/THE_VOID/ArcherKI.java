package THE_VOID;

/*
            if(xPlayer == xPos){                                    //PLAYER auf X-Achse von Archer
                if(yPlayer == yPos){}                               //  //PLAYER pos = Archer pos
                else if(yPlayer > yPos){movementY = 1f;}            //  //PLAYER ueber Archer
                else if(yPlayer < yPos){movementY = -1f;}           //  //PLAYER unter Archer
            }                                                       //
            else if(yPlayer == yPos){                               //PLAYER auf Y-Achse von Archer
                if(xPlayer == xPos){}                               //  //PLAYER pos = Archer pos
                else if(xPlayer > xPos){movementX = 1f;}            //  //PLAYER rechts von Archer
                else if(xPlayer < xPos){movementX = -1f;}           //  //PLAYER links von Archer
            }                                                       //
            else if(xPlayer > xPos){                                //PLAYER rechts von Archer
                if(yPlayer > yPos){                                 //      //PLAYER ist im Quadrant I
                    if((yPlayer - yPos) > (xPlayer - xPos)){        //      //  //Weg zu PLAYER x kuerzer als zu PLAYER y
                        movementX = 1f;                             //      //  //
                    }                                               //      //  //
                    else{                                           //      //  //Weg zu PLAYER y kuerzer als zu PLAYER x
                        movementY = 1f;                             //      //
                    }                                               //      //
                }                                                   //      //
                else if(yPlayer < yPos){                            //      //PLAYER ist im Quadrant II
                    if((yPos - yPlayer) > (xPlayer - xPos)){        //          //Weg zu PLAYER x kuerzer als zu PLAYER y
                        movementX = 1f;                             //          //
                    }                                               //          //
                    else{                                           //          //Weg zu PLAYER y kuerzer als zu PLAYER y
                        movementY = -1f;                            //
                    }                                               //
                }                                                   //
            }                                                       //
            else if(xPlayer < xPos){                                //PLAYER links von Archer
                if(yPlayer < yPos){                                 //        //PLAYER ist im Quadrant III
                    if((yPlayer - yPos) > (xPlayer - xPos)){        //        //  //Weg zu PLAYER x kuerzer als zu PLAYER y
                        movementX = -1f;                            //        //  //
                    }                                               //        //  //
                    else{                                           //        //  //Weg zu PLAYER y kuerzer als zu PLAYER x
                        movementY = -1f;                            //        //
                    }                                               //        //
                }                                                   //        //
                else if(yPlayer > yPos){                            //        //PLAYER ist im Quadrant IV
                    if((yPlayer - yPos) > (xPos - xPlayer)){        //            //Weg zu PLAYER x kuerzer als zu PLAYER y
                        movementX = -1;                             //            //
                    }                                               //            //
                    else{                                           //            //Weg zu PLAYER y kuerzer als zu PLAYER x
                        movementY = 1;                              //
                    }                                               ////////////
                }
            }
        
        if(yPos + movementY < 0){
        
        }
        else if(xPos + movementX < 0){
        
        }*/ 


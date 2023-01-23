package game;

public class Cell {
    public Element element;
    public int x, y, sinceMovement;
    Game game;

    public Cell(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        element = new Element(x, y, "empty");
    }
    
    public void update() {
        //Does nothing if element has already been updates
        if (element.hasUpdated)
            return;
        //If falling solid
        if (element.getState() == 2) {
            //Falling directly below
            if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 5) {
                //TODO replace with formula
                if (element.deltaY < 0.05)
                    element.deltaY = 0.05;
                else if (element.deltaY < 0.25)
                    element.changeDeltaY(0.005);
                else if (element.deltaY < 0.5)
                    element.changeDeltaY(0.001);
                element.updatePosition();
                if (element.yOffset > 1) {
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, "empty");
                }
            }
            else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 5 && Math.random() < 0.666) {
                if (element.deltaY < 0.025)
                    element.deltaY = 0.025;
                else if (element.deltaY < 0.1)
                    element.changeDeltaY(0.0025);
                else if (element.deltaY < 0.52)
                    element.changeDeltaY(0.0005);
                if (element.deltaX < 0.05)
                    element.deltaX = 0.05;
                else if (element.deltaX < 0.25)
                    element.changeDeltaX(0.05);
                else if (element.deltaX < 0.5)
                    element.changeDeltaX(0.1);
                element.updatePosition();
                if (element.xOffset > 1 && element.yOffset > 1) {
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, "empty");
                }
                else if (element.xOffset > 1) {
                    game.setElement(x+1, y, this.element);
                    game.setElement(x, y, "empty");
                }
            }
            else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 5 && Math.random() < 0.666) {
                if (element.deltaY < 0.025)
                    element.deltaY = 0.025;
                else if (element.deltaY < 0.1)
                    element.changeDeltaY(0.0025);
                else if (element.deltaY < 0.52)
                    element.changeDeltaY(0.0005);
                if (element.deltaX > -0.05)
                    element.deltaX = -0.05;
                else if (element.deltaX > -0.25)
                    element.changeDeltaX(-0.05);
                else if (element.deltaX > -0.5)
                    element.changeDeltaX(-0.1);
                element.updatePosition();
                if (element.xOffset > 1 && element.yOffset < -1) {
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, "empty");
                }
                else if (element.xOffset < -1) {
                    game.setElement(x-1, y, this.element);
                    game.setElement(x, y, "empty");
                }
            }
            else if (game.checkInBounds(x, y+1) && (game.grid[x][y+1].element.getState() == 3 || game.grid[x][y+1].element.getState() == 4) && Math.random() < 0.3) {
                Element tempElement = game.grid[x][y+1].element;
                game.setElement(x, y+1, this.element);
                game.setElement(x, y, tempElement);;
            }
            else if (game.checkInBounds(x+1, y+1) && (game.grid[x+1][y+1].element.getState() == 3 || game.grid[x+1][y+1].element.getState() == 4) && Math.random() < 0.1) {
                Element tempElement = game.grid[x+1][y+1].element;
                game.setElement(x+1, y+1, this.element);
                game.setElement(x, y, tempElement);;
            }
            else if (game.checkInBounds(x-1, y+1) && (game.grid[x-1][y+1].element.getState() == 3 || game.grid[x-1][y+1].element.getState() == 4) && Math.random() < 0.1) {
                Element tempElement = game.grid[x-1][y+1].element;
                game.setElement(x-1, y+1, this.element);
                game.setElement(x, y, tempElement);;
            }
        }
        //If liquid
        if (element.getState() == 3) {
            if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 5) {
                game.setElement(x, y+1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 5 && Math.random() < 1*element.viscosity) {
                game.setElement(x+1, y+1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 5 && Math.random() < 1*element.viscosity) {
                game.setElement(x-1, y+1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (Math.random() < element.viscosity) {
                //Special interactions for water
                if (element.ID.equals("water")) {
                    //Forms stone with lava
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("lava")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x, y+1, "stone");
                        return;
                    }
                    if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID.equals("lava")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x+1, y+1, "stone");
                        return;
                    }
                    if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID.equals("lava")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x-1, y+1, "stone");
                        return;
                    }
                }
                //Special interactions for lava
                if (element.ID.equals("lava")) {
                    //Forms stone with water
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("water")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x, y+1, "stone");
                        return;
                    }
                    if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID.equals("water")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x+1, y+1, "stone");
                        return;
                    }
                    if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID.equals("water")) {
                        game.setElement(x, y, "steam");
                        game.setElement(x-1, y+1, "stone");
                        return;
                    }

                    //Melts sand if below
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("sand")) {
                        if (Math.random() < 0.5)
                            game.setElement(x, y, "empty");
                            game.setElement(x, y+1, "lava");
                        return;
                    }
                }
                //Special interactions for acid
                if (element.ID.equals("acid")) {
                    //Melts everything below and to the side
                    if (game.checkInBounds(x, y+1) && !game.grid[x][y+1].element.ID.equals("acid") && game.grid[x][y+1].element.getState() != 5) {
                        game.setElement(x, y, "empty");
                        if (Math.random() < 0.9)
                            game.setElement(x, y+1, "acid");
                        else
                            game.setElement(x, y+1, "empty");
                        return;
                    }
                    if (game.checkInBounds(x+1, y) && !game.grid[x+1][y].element.ID.equals("acid") && game.grid[x+1][y].element.getState() != 5) {
                        game.setElement(x, y, "empty");
                        if (Math.random() < 0.9)
                            game.setElement(x+1, y, "acid");
                        else
                            game.setElement(x+1, y, "empty");
                        return;
                    }
                    if (game.checkInBounds(x-1, y) && !game.grid[x-1][y].element.ID.equals("acid") && game.grid[x-1][y].element.getState() != 5) {
                        game.setElement(x, y, "empty");
                        if (Math.random() < 0.9)
                            game.setElement(x-1, y, "acid");
                        else
                            game.setElement(x-1, y, "empty");
                        return;
                    }
                }
                //Handles everything besides acid
                else {
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("acid")) {
                        game.setElement(x, y, "empty");
                        if (Math.random() < 0.9)
                            game.setElement(x, y+1, "acid");
                        else
                            game.setElement(x, y+1, "empty");
                        return;
                    }
                }

                //Flowing right first
                if (Math.random() < 0.5) {
                    int totalDistance = (int) (Math.random()*6*element.viscosity+1);
                    int xOffset = 1;
                    int distance = 0;
                    boolean hasMoved = false;
                    //Trying to flow right
                    while (xOffset <= totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset-1;
                            xOffset = -1;
                            break;
                        }
                        xOffset++;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                    
                    //Trying to flow left
                    while (xOffset >= -totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset+1;
                            break;
                        }
                        xOffset--;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                }
                //Flowing left first
                else {
                    int totalDistance = (int) (Math.random()*6*element.viscosity+1);
                    int xOffset = -1;
                    int distance = 0;
                    boolean hasMoved = false;
                    //Trying to flow left
                    while (xOffset >= -totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset+1;
                            xOffset = 1;
                            break;
                        }
                        xOffset--;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                    
                    //Trying to flow right
                    while (xOffset <= totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset-1;
                            break;
                        }
                        xOffset++;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                }
            }
        }
        //If gas
        if (element.getState() == 4) {
            if (game.checkInBounds(x, y-1) && game.grid[x][y-1].element.getState() == 5 && Math.random() < 0.8) {
                this.element.hasUpdated = true;
                game.setElement(x, y-1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (game.checkInBounds(x+1, y-1) && game.grid[x+1][y-1].element.getState() == 5 && Math.random() < 0.5) {
                this.element.hasUpdated = true;
                game.setElement(x+1, y-1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (game.checkInBounds(x-1, y-1) && game.grid[x-1][y-1].element.getState() == 5 && Math.random() < 0.5) {
                this.element.hasUpdated = true;
                game.setElement(x-1, y-1, this.element);
                game.setElement(x, y, "empty");
            }
            else if (game.checkInBounds(x, y-1) && (game.grid[x][y-1].element.getState() == 3 || game.grid[x][y-1].element.getState() == 4) && Math.random() < 0.6) {
                this.element.hasUpdated = true;
                Element tempElement = game.grid[x][y-1].element;
                game.setElement(x, y-1, this.element);
                this.element = tempElement;
            }
            else if (game.checkInBounds(x+1, y-1) && (game.grid[x+1][y-1].element.getState() == 3 || game.grid[x+1][y-1].element.getState() == 4) && Math.random() < 0.3) {
                this.element.hasUpdated = true;
                Element tempElement = game.grid[x+1][y-1].element;
                game.setElement(x+1, y-1, this.element);
                this.element = tempElement;
            }
            else if (game.checkInBounds(x-1, y-1) && (game.grid[x-1][y-1].element.getState() == 3 || game.grid[x-1][y-1].element.getState() == 4) && Math.random() < 0.3) {
                this.element.hasUpdated = true;
                Element tempElement = game.grid[x-1][y-1].element;
                game.setElement(x-1, y-1, this.element);
                this.element = tempElement;
            }
            else {
                //Converts steam to water over time (1/50,000 chance per update)
                if (Math.random() < 0.0001 && element.ID.equals("steam")) {
                    if (Math.random() < 0.1)
                    game.setElement(x, y, "water");
                    else
                        game.setElement(x, y, "empty");
                    return;
                }
                //Flowing right first
                if (Math.random() < 0.5) {
                    int totalDistance = (int) (Math.random()*2+1);
                    int xOffset = 1;
                    int distance = 0;
                    boolean hasMoved = false;
                    //Trying to flow right
                    while (xOffset <= totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset-1;
                            xOffset = -1;
                            break;
                        }
                        xOffset++;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                    
                    //Trying to flow left
                    while (xOffset >= -totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset+1;
                            break;
                        }
                        xOffset--;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                }
                //Flowing left first
                else {
                    int totalDistance = (int) (Math.random()*3+1);
                    int xOffset = -1;
                    int distance = 0;
                    boolean hasMoved = false;
                    //Trying to flow left
                    while (xOffset >= -totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset+1;
                            xOffset = 1;
                            break;
                        }
                        xOffset--;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                    
                    //Trying to flow right
                    while (xOffset <= totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 5) {
                            distance = xOffset;
                            hasMoved = true;
                        }
                        else {
                            distance = xOffset-1;
                            break;
                        }
                        xOffset++;
                    }
                    if (hasMoved) {
                        game.setElement(x+distance, y, this.element);
                        game.setElement(x, y, "empty");
                        return;
                    }
                }
            }

        }
    }
}

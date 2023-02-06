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

        //If static
        if (element.getState() == 1) {
            if (element.ID.equals("sponge")) {
                if (Math.random() < 0.5) {
                    if (game.checkInBounds(x-1, y-1) && game.grid[x-1][y-1].element.ID.equals("water")) {
                        game.setElement(x-1, y-1, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x, y-1) && game.grid[x][y-1].element.ID.equals("water")) {
                        game.setElement(x, y-1, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x+1, y-1) && game.grid[x+1][y-1].element.ID.equals("water")) {
                        game.setElement(x+1, y-1, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID.equals("water")) {
                        game.setElement(x-1, y, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID.equals("water")) {
                        game.setElement(x+1, y, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.ID.equals("water")) {
                        game.setElement(x-1, y+1, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("water")) {
                        game.setElement(x, y+1, "empty");
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.ID.equals("water")) {
                        game.setElement(x+1, y+1, "empty");
                        element.saturated = true;
                        return;
                    }
                }
            }

            return;
        }

        //If falling solid
        if (element.getState() == 2) {
            if (element.checkIfMoving()) {
                int xOffset = 0;
                int yOffset = 0;
                int lastYOffset = 0;
                int vx;
                if (element.VX > 0)
                    vx = (int) element.VX;
                else
                    vx = (int) Math.abs(element.VX)*-1;
                int vy = (int) element.VY;
                int finalX = x, finalY = y;
                boolean collidedX = false, collidedY = false;
                if (Math.abs(vx) >= 1) {
                    if (vx > 0) {
                        while (xOffset <= vx) {
                            if (!collidedX && (!game.checkInBounds(x+xOffset, y+lastYOffset) || game.grid[x+xOffset][y+lastYOffset].element.getState() < 4 && !game.grid[x+xOffset][y+lastYOffset].element.checkIfMoving())) {
                                collidedX = true;
                                finalX = x+xOffset-1;
                            }

                            yOffset = (int) ((double) xOffset/vx*vy);
                            if (!collidedY && 
                                ((!collidedX && (!game.checkInBounds(x+xOffset, y+yOffset) || game.grid[x+xOffset][y+yOffset].element.getState() < 4 && !game.grid[x+xOffset][y+yOffset].element.checkIfMoving())) || 
                                (collidedX && (!game.checkInBounds(finalX, y+yOffset) || game.grid[finalX][y+yOffset].element.getState() < 4 && !game.grid[finalX][y+yOffset].element.checkIfMoving())))) {
                                collidedY = true;
                                if (yOffset > 0)
                                    finalY = y+yOffset-1;
                                else
                                    finalY = y+yOffset+1;
                            }

                            if (collidedX && collidedY)
                                break;
                            if (!collidedY)
                                lastYOffset = (int) ((double) (xOffset)/vx*vy);
                            xOffset++;
                        }
                    }
                    else {
                        while (xOffset >= vx) {
                            if (!collidedX && (!game.checkInBounds(x+xOffset, y+lastYOffset) || game.grid[x+xOffset][y+lastYOffset].element.getState() < 4 && !game.grid[x+xOffset][y+lastYOffset].element.checkIfMoving())) {
                                collidedX = true;
                                finalX = x+xOffset+1;
                            }

                            yOffset = (int) ((double) xOffset/vx*vy);
                            if (!collidedY && 
                                ((!collidedX && (!game.checkInBounds(x+xOffset, y+yOffset) || game.grid[x+xOffset][y+yOffset].element.getState() < 4 && !game.grid[x+xOffset][y+yOffset].element.checkIfMoving())) || 
                                (collidedX && (!game.checkInBounds(finalX, y+yOffset) || game.grid[finalX][y+yOffset].element.getState() < 4 && !game.grid[finalX][y+yOffset].element.checkIfMoving())))) {
                                collidedY = true;
                                if (yOffset > 0)
                                    finalY = y+yOffset-1;
                                else
                                    finalY = y+yOffset+1;
                            }

                            if (collidedX && collidedY)
                                break;
                            if (!collidedY)
                                lastYOffset = (int) ((double) (xOffset)/vx*vy);
                            xOffset--;
                        }
                    }
                    if (collidedX)
                        element.VX /= -10;
                    else {
                        if (vx > 0)
                            finalX = x+xOffset-1;
                        else 
                            finalX = x+xOffset+1;
                    }
                    if (collidedY) {
                        if (element.VY > 0) {
                            element.VX *= 0.75;
                        }
                        element.VY /= -10;
                    }
                    else {
                        finalY = y+yOffset;
                    }
                }
                else {
                    if (vy > 0) {
                        while (yOffset <= vy) {
                            if (!game.checkInBounds(x, y+yOffset) || game.grid[x][y+yOffset].element.getState() < 4 && !game.grid[x][y+yOffset].element.checkIfMoving()) {
                                collidedY = true;
                                finalY = y+yOffset-1;
                                break;
                            }

                            yOffset++;
                        }
                    }
                    else {
                        while (yOffset >= vy) {
                            if (!game.checkInBounds(x, y+yOffset) || game.grid[x][y+yOffset].element.getState() < 4 && !game.grid[x][y+yOffset].element.checkIfMoving()) {
                                collidedY = true;
                                finalY = y+yOffset+1;
                                break;
                            }

                            yOffset--;
                        }
                    }
                    if (collidedY) {
                        if (element.VY > 0) {
                            element.VX *= 0.75;
                        }
                        element.VY /= -10;
                    }
                    else {
                        if (yOffset > 0)
                            finalY = y+yOffset-1;
                        else
                            finalY = y+yOffset+1;
                    }
                }

                //Updates the element in the cell before changing its position
                element.hasUpdated = true;
                element.decreaseVelocity();

                if (game.checkInBounds(finalX, finalY) && game.grid[finalX][finalY].element.checkIfMoving() && game.grid[finalX][finalY].element.getState() < 4) {
                    if (game.grid[finalX][finalY].element.timeSinceLastMove > 5) {
                        element.VX *= 0.75;
                        element.VY = 1;
                    }
                    return;
                }

                if (finalX != x || finalY != y) {
                    game.setElement(finalX, finalY, this.element);
                    game.setElement(x, y, "empty");
                }
            }
            else {
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 5) {
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, "empty");
                }
                else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 5 && Math.random() < 0.666) {
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, "empty");
                }
                else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 5 && Math.random() < 0.666) {
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, "empty");
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
            return;
            }
        }
            
        //If liquid
        if (element.getState() == 3) {
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
                    game.setElement(x+1, y, "stone");
                    return;
                }
                if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID.equals("lava")) {
                    game.setElement(x, y, "steam");
                    game.setElement(x-1, y, "stone");
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
                    game.setElement(x+1, y, "stone");
                    return;
                }
                if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID.equals("water")) {
                    game.setElement(x, y, "steam");
                    game.setElement(x-1, y, "stone");
                    return;
                }

                //Destroys sponge; gets cooled if sponge is saturated
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID.equals("sponge")) {
                    if (game.grid[x][y+1].element.saturated) {
                        game.grid[x][y+1].element.saturated = false;
                        game.setElement(x, y, "steam");
                    }
                    else {
                        game.setElement(x, y+1, "lava");
                        game.setElement(x, y, "empty");
                    }
                    return;
                }
                if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y].element.ID.equals("sponge")) {
                    if (game.grid[x+1][y+1].element.saturated) {
                        game.grid[x+1][y+1].element.saturated = false;
                        game.setElement(x, y, "steam");
                    }
                    else {
                        game.setElement(x+1, y+1, "lava");
                        game.setElement(x, y, "empty");
                    }
                    return;
                }
                if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y].element.ID.equals("sponge")) {
                    if (game.grid[x-1][y+1].element.saturated) {
                        game.grid[x-1][y+1].element.saturated = false;
                        game.setElement(x, y, "steam");
                    }
                    else {
                        game.setElement(x-1, y+1, "lava");
                        game.setElement(x, y, "empty");
                    }
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

            if (element.checkIfMoving()) {
                int xOffset = 0;
                int yOffset = 0;
                int lastYOffset = 0;
                int vx;
                if (element.VX > 0)
                    vx = (int) element.VX;
                else
                    vx = (int) Math.abs(element.VX)*-1;
                int vy = (int) element.VY;
                int finalX = x, finalY = y;
                boolean collidedX = false, collidedY = false;
                if (Math.abs(vx) >= 1) {
                    if (vx > 0) {
                        while (xOffset <= vx) {
                            if (!collidedX && (!game.checkInBounds(x+xOffset, y+lastYOffset) || game.grid[x+xOffset][y+lastYOffset].element.getState() < 4 && !game.grid[x+xOffset][y+lastYOffset].element.checkIfMoving())) {
                                collidedX = true;
                                finalX = x+xOffset-1;
                            }

                            yOffset = (int) ((double) xOffset/vx*vy);
                            if (!collidedY && 
                                ((!collidedX && (!game.checkInBounds(x+xOffset, y+yOffset) || game.grid[x+xOffset][y+yOffset].element.getState() < 4 && !game.grid[x+xOffset][y+yOffset].element.checkIfMoving())) || 
                                (collidedX && (!game.checkInBounds(finalX, y+yOffset) || game.grid[finalX][y+yOffset].element.getState() < 4 && !game.grid[finalX][y+yOffset].element.checkIfMoving())))) {
                                collidedY = true;
                                if (yOffset > 0)
                                    finalY = y+yOffset-1;
                                else
                                    finalY = y+yOffset+1;
                            }

                            if (collidedX && collidedY)
                                break;
                            if (!collidedY)
                                lastYOffset = (int) ((double) (xOffset)/vx*vy);
                            xOffset++;
                        }
                    }
                    else {
                        while (xOffset >= vx) {
                            if (!collidedX && (!game.checkInBounds(x+xOffset, y+lastYOffset) || game.grid[x+xOffset][y+lastYOffset].element.getState() < 4 && !game.grid[x+xOffset][y+lastYOffset].element.checkIfMoving())) {
                                collidedX = true;
                                finalX = x+xOffset+1;
                            }

                            yOffset = (int) ((double) xOffset/vx*vy);
                            if (!collidedY && 
                                ((!collidedX && (!game.checkInBounds(x+xOffset, y+yOffset) || game.grid[x+xOffset][y+yOffset].element.getState() < 4 && !game.grid[x+xOffset][y+yOffset].element.checkIfMoving())) || 
                                (collidedX && (!game.checkInBounds(finalX, y+yOffset) || game.grid[finalX][y+yOffset].element.getState() < 4 && !game.grid[finalX][y+yOffset].element.checkIfMoving())))) {
                                collidedY = true;
                                if (yOffset > 0)
                                    finalY = y+yOffset-1;
                                else
                                    finalY = y+yOffset+1;
                            }

                            if (collidedX && collidedY)
                                break;
                            if (!collidedY)
                                lastYOffset = (int) ((double) (xOffset)/vx*vy);
                            xOffset--;
                        }
                    }
                    if (collidedX)
                        element.VX /= -10;
                    else {
                        if (vx > 0)
                            finalX = x+xOffset-1;
                        else 
                            finalX = x+xOffset+1;
                    }
                    if (collidedY) {
                        if (element.VY > 0) {
                            element.VX *= 0.75;
                        }
                        element.VY /= -10;
                    }
                    else {
                        finalY = y+yOffset;
                    }
                }
                else {
                    if (vy > 0) {
                        while (yOffset <= vy) {
                            if (!game.checkInBounds(x, y+yOffset) || game.grid[x][y+yOffset].element.getState() < 4 && !game.grid[x][y+yOffset].element.checkIfMoving()) {
                                collidedY = true;
                                finalY = y+yOffset-1;
                                break;
                            }

                            yOffset++;
                        }
                    }
                    else {
                        while (yOffset >= vy) {
                            if (!game.checkInBounds(x, y+yOffset) || game.grid[x][y+yOffset].element.getState() < 4 && !game.grid[x][y+yOffset].element.checkIfMoving()) {
                                collidedY = true;
                                finalY = y+yOffset+1;
                                break;
                            }

                            yOffset--;
                        }
                    }
                    if (collidedY) {
                        if (element.VY > 0) {
                            element.VX *= 0.75;
                        }
                        element.VY /= -10;
                    }
                    else {
                        if (yOffset > 0)
                            finalY = y+yOffset-1;
                        else
                            finalY = y+yOffset+1;
                    }
                }

                //Updates the element in the cell before changing its position
                element.hasUpdated = true;
                element.decreaseVelocity();

                if (game.grid[finalX][finalY].element.checkIfMoving() && game.grid[finalX][finalY].element.getState() < 4) {
                    if (game.grid[finalX][finalY].element.timeSinceLastMove > 5) {
                        element.VX *= 0.75;
                        element.VY = 1;
                    }
                    return;
                }

                if (finalX != x || finalY != y) {
                    game.setElement(finalX, finalY, this.element);
                    game.setElement(x, y, "empty");
                }
            }
            else {
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
                return;
            }
        }
        
        //If gas
        if (element.getState() == 4) {
            //Special interactions for steam
            if (Math.random() < 0.0001 && element.ID.equals("steam")) {
                //Condensation (1/10,000 chance per update)
                if (Math.random() < 0.1)
                    game.setElement(x, y, "water");
                else
                    game.setElement(x, y, "empty");
                return;
            }
            //Special interactions for flame
            if (element.ID.equals("flame")) {
                if (Math.random() < 0.05) {
                    game.setElement(x, y, "empty");
                    return;
                }
            }

            if (game.checkInBounds(x, y-1) && game.grid[x][y-1].element.getState() == 5 && Math.random() < 0.8) {
                this.element.hasUpdated = true;
                game.setElement(x, y-1, this.element);
                game.setElement(x, y, "empty");
                return;
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
            return;
        }
    }
}

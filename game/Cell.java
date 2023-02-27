package game;

public class Cell {
    public Element element;
    public int x, y, sinceMovement;
    Game game;

    public Cell(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        element = new Element(x, y, Element.Type.MPTY);
    }
    
    public void update() {
        //Does nothing if element has already been updates
        if (element.hasUpdated)
            return;

        //If static
        if (element.getState() == 1) {
            if (element.ID == Element.Type.SPNG) {
                if (Math.random() < 0.5) {
                    if (game.checkInBounds(x-1, y-1) && game.grid[x-1][y-1].element.ID == Element.Type.WATR) {
                        game.setElement(x-1, y-1, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x, y-1) && game.grid[x][y-1].element.ID == Element.Type.WATR) {
                        game.setElement(x, y-1, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x-1, y-1) && game.grid[x-1][y-1].element.ID == Element.Type.WATR) {
                        game.setElement(x+1, y-1, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID == Element.Type.WATR) {
                        game.setElement(x-1, y, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID == Element.Type.WATR) {
                        game.setElement(x+1, y, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.ID == Element.Type.WATR) {
                        game.setElement(x-1, y+1, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.WATR) {
                        game.setElement(x, y+1, Element.Type.MPTY);
                        element.saturated = true;
                        return;
                    }
                    if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.ID == Element.Type.WATR) {
                        game.setElement(x+1, y+1, Element.Type.MPTY);
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
                element.decreaseVelocity();
                for (int i = 0; i < 10; i++) {
                    element.y += element.vy/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 2 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.y -= element.vy/10;
                        if (element.timeSinceLastMove > 5)
                            element.vy *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    (game.getElement(element.x, element.y).getState() > 0 && game.getElement(element.x, element.y).getState() <= 2) && ((int) element.x != x || (int) element.y != y)) {
                        element.y -= element.vy/10;
                        element.vy = 0;
                    }
                    else if (game.getElement(element.x, element.y).getState() == 3) {
                        element.vy *= 0.9;
                        Element tempElement = game.getElement(element.x, element.y);
                        game.setElement(element.x, element.y, this.element);
                        game.setElement(x, y, tempElement);;
                    }
                    
                    element.x += element.vx/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 2 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.x -= element.vx/10;
                        if (element.timeSinceLastMove > 5)
                            element.vx *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    (game.getElement(element.x, element.y).getState() > 0 && game.getElement(element.x, element.y).getState() <= 2) && ((int) element.x != x || (int) element.y != y)) {
                        element.x -= element.vx/10;
                        element.vx = 0;
                    }
                    else if (game.getElement(element.x, element.y).getState() == 3) {
                        element.vx *= 0.9;
                        Element tempElement = game.getElement(element.x, element.y);
                        game.setElement(element.x, element.y, this.element);
                        game.setElement(x, y, tempElement);;
                    }

                    if (element.vx == 0 && element.vy == 0)
                        break;
                }
                game.setElement(element.x, element.y, element);
                if ((int) element.x != x || (int) element.y != y) {
                    element.timeSinceLastMove = 0;
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else {
                    element.timeSinceLastMove++;
                }
            }
            else if (game.time % 2 == 0) {
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x, y+1)) {
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x+1, y+1) && Math.random() < 0.666) {
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x-1, y+1) && Math.random() < 0.666) {
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x, y+1) && (game.grid[x][y+1].element.getState() == 3 || game.grid[x][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x, y+1) && Math.random() < 0.3) {
                    Element tempElement = game.grid[x][y+1].element;
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, tempElement);;
                }
                else if (game.checkInBounds(x+1, y+1) && (game.grid[x+1][y+1].element.getState() == 3 || game.grid[x+1][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x+1, y+1) && Math.random() < 0.1) {
                    Element tempElement = game.grid[x+1][y+1].element;
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, tempElement);;
                }
                else if (game.checkInBounds(x-1, y+1) && (game.grid[x-1][y+1].element.getState() == 3 || game.grid[x-1][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x-1, y+1) && Math.random() < 0.1) {
                    Element tempElement = game.grid[x-1][y+1].element;
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, tempElement);
                }
            }
            else {
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x, y+1)) {
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x-1, y+1) && Math.random() < 0.666) {
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 0 && !game.player.checkIfCellIntersects(x+1, y+1) && Math.random() < 0.666) {
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x, y+1) && (game.grid[x][y+1].element.getState() == 3 || game.grid[x][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x, y+1) && Math.random() < 0.3) {
                    Element tempElement = game.grid[x][y+1].element;
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, tempElement);;
                }
                else if (game.checkInBounds(x-1, y+1) && (game.grid[x-1][y+1].element.getState() == 3 || game.grid[x-1][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x-1, y+1) && Math.random() < 0.1) {
                    Element tempElement = game.grid[x-1][y+1].element;
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, tempElement);;
                }
                else if (game.checkInBounds(x+1, y+1) && (game.grid[x+1][y+1].element.getState() == 3 || game.grid[x+1][y+1].element.getState() == 4) && !game.player.checkIfCellIntersects(x+1, y+1) && Math.random() < 0.1) {
                    Element tempElement = game.grid[x+1][y+1].element;
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, tempElement);
                }
            return;
            }
        }
            
        //If liquid
        if (element.getState() == 3) {
            //Special interactions for water
            if (element.ID == Element.Type.WATR) {
                //Forms stone with lava
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.LAVA) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x, y+1, Element.Type.STNE);
                    return;
                }
                if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID == Element.Type.LAVA) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x+1, y, Element.Type.STNE);
                    return;
                }
                if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID == Element.Type.LAVA) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x-1, y, Element.Type.STNE);
                    return;
                }
            }
            //Special interactions for lava
            if (element.ID == Element.Type.LAVA) {
                //Forms stone with water
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.WATR) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x, y+1, Element.Type.STNE);
                    return;
                }
                if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID == Element.Type.WATR) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x+1, y, Element.Type.STNE);
                    return;
                }
                if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID == Element.Type.WATR) {
                    game.setElement(x, y, Element.Type.VAPR);
                    game.setElement(x-1, y, Element.Type.STNE);
                    return;
                }

                //Destroys sponge; gets cooled if sponge is saturated
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.WATR) {
                    if (game.grid[x][y+1].element.saturated) {
                        game.grid[x][y+1].element.saturated = false;
                        game.setElement(x, y, Element.Type.VAPR);
                    }
                    else {
                        game.setElement(x, y+1, Element.Type.LAVA);
                        game.setElement(x, y, Element.Type.MPTY);
                    }
                    return;
                }
                if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y].element.ID == Element.Type.SPNG) {
                    if (game.grid[x+1][y+1].element.saturated) {
                        game.grid[x+1][y+1].element.saturated = false;
                        game.setElement(x, y, Element.Type.VAPR);
                    }
                    else {
                        game.setElement(x+1, y+1, Element.Type.LAVA);
                        game.setElement(x, y, Element.Type.MPTY);
                    }
                    return;
                }
                if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y].element.ID == Element.Type.SPNG) {
                    if (game.grid[x-1][y+1].element.saturated) {
                        game.grid[x-1][y+1].element.saturated = false;
                        game.setElement(x, y, Element.Type.VAPR);
                    }
                    else {
                        game.setElement(x-1, y+1, Element.Type.LAVA);
                        game.setElement(x, y, Element.Type.LAVA);
                    }
                    return;
                }

                //Melts sand if below
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.SAND) {
                    if (Math.random() < 0.5)
                        game.setElement(x, y, Element.Type.MPTY);
                        game.setElement(x, y+1, Element.Type.LAVA);
                    return;
                }
            }
            //Special interactions for acid
            if (element.ID == Element.Type.ACID) {
                //Melts everything below and to the side
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID != Element.Type.ACID && game.grid[x][y+1].element.getState() != 0) {
                    game.setElement(x, y, Element.Type.MPTY);
                    if (Math.random() < 0.9)
                        game.setElement(x, y+1, Element.Type.ACID);
                    else
                        game.setElement(x, y+1, Element.Type.MPTY);
                    return;
                }
                if (game.checkInBounds(x+1, y) && game.grid[x+1][y].element.ID != Element.Type.ACID && game.grid[x+1][y].element.getState() != 0) {
                    game.setElement(x, y, Element.Type.MPTY);
                    if (Math.random() < 0.9)
                        game.setElement(x+1, y, Element.Type.ACID);
                    else
                        game.setElement(x+1, y, Element.Type.MPTY);
                    return;
                }
                if (game.checkInBounds(x-1, y) && game.grid[x-1][y].element.ID != Element.Type.ACID && game.grid[x-1][y].element.getState() != 0) {
                    game.setElement(x, y, Element.Type.MPTY);
                    if (Math.random() < 0.9)
                        game.setElement(x-1, y, Element.Type.ACID);
                    else
                        game.setElement(x-1, y, Element.Type.MPTY);
                    return;
                }
            }
            //Handles everything besides acid
            else {
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.ID == Element.Type.ACID) {
                    game.setElement(x, y, Element.Type.MPTY);
                    if (Math.random() < 0.9)
                        game.setElement(x, y+1, Element.Type.ACID);
                    else
                        game.setElement(x, y+1, Element.Type.MPTY);
                    return;
                }
            }

            if (element.checkIfMoving()) {
                element.decreaseVelocity();
                for (int i = 0; i < 10; i++) {
                    element.y += element.vy/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 3 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.y -= element.vy/10;
                        if (element.timeSinceLastMove > 5)
                            element.vy *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    (game.getElement(element.x, element.y).getState() > 0 && game.getElement(element.x, element.y).getState() <= 3) && ((int) element.x != x || (int) element.y != y)) {
                        element.y -= element.vy/10;
                        element.vy = 0;
                    }
                    else if (game.getElement(element.x, element.y).getState() == 4) {
                        element.vy *= 0.9;
                        Element tempElement = game.getElement(element.x, element.y);
                        game.setElement(element.x, element.y, this.element);
                        game.setElement(x, y, tempElement);;
                    }
                    
                    element.x += element.vx/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 3 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.x -= element.vx/10;
                        if (element.timeSinceLastMove > 5)
                            element.vx *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    (game.getElement(element.x, element.y).getState() > 0 && game.getElement(element.x, element.y).getState() <= 3) && ((int) element.x != x || (int) element.y != y)) {
                        element.x -= element.vx/10;
                        element.vx = 0;
                    }
                    else if (game.getElement(element.x, element.y).getState() == 4) {
                        element.vx *= 0.9;
                        Element tempElement = game.getElement(element.x, element.y);
                        game.setElement(element.x, element.y, this.element);
                        game.setElement(x, y, tempElement);;
                    }

                    if (element.vx == 0 && element.vy == 0)
                        break;
                }
                game.setElement(element.x, element.y, element);
                if ((int) element.x != x || (int) element.y != y) {
                    element.timeSinceLastMove = 0;
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else {
                    element.timeSinceLastMove++;
                }
            }
            else {
                if (game.checkInBounds(x, y+1) && game.grid[x][y+1].element.getState() == 0) {
                    game.setElement(x, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x+1, y+1) && game.grid[x+1][y+1].element.getState() == 0 && Math.random() < 1*element.viscosity) {
                    game.setElement(x+1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x-1, y+1) && game.grid[x-1][y+1].element.getState() == 0 && Math.random() < 1*element.viscosity) {
                    game.setElement(x-1, y+1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
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
                            if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                            game.setElement(x, y, Element.Type.MPTY);
                            return;
                        }
                        
                        //Trying to flow left
                        while (xOffset >= -totalDistance) {
                            if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                            game.setElement(x, y, Element.Type.MPTY);
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
                            if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                            game.setElement(x, y, Element.Type.MPTY);
                            return;
                        }
                        
                        //Trying to flow right
                        while (xOffset <= totalDistance) {
                            if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                            game.setElement(x, y, Element.Type.MPTY);
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
            if (Math.random() < 0.0001 && element.ID != Element.Type.VAPR) {
                //Condensation (1/10,000 chance per update)
                if (Math.random() < 0.1)
                    game.setElement(x, y, Element.Type.WATR);
                else
                    game.setElement(x, y, Element.Type.MPTY);
                return;
            }
            //Special interactions for flame
            if (element.ID == Element.Type.FIRE) {
                if (Math.random() < 0.05) {
                    game.setElement(x, y, Element.Type.MPTY);
                    return;
                }
            }

            if (element.checkIfMoving()) {
                element.decreaseVelocity();
                for (int i = 0; i < 10; i++) {
                    element.y += element.vy/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 4 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.y -= element.vy/10;
                        if (element.timeSinceLastMove > 5)
                            element.vy *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    game.getElement(element.x, element.y).getState() != 0 && ((int) element.x != x || (int) element.y != y)) {
                        element.y -= element.vy/10;
                        element.vy = 0;
                    }
                    
                    element.x += element.vx/10;

                    if (game.checkInBounds(element.x, element.y) && game.getElement(element.x, element.y).getState() == 4 && ((int) element.x != x || (int) element.y != y) && game.getElement(element.x, element.y).checkIfMoving()) {
                        element.x -= element.vx/10;
                        if (element.timeSinceLastMove > 5)
                            element.vx *= 0.95;
                    }
                    else if (!game.checkInBounds(element.x, element.y) || 
                    game.player.checkIfCellIntersects((int) element.x, (int) element.y) || 
                    game.getElement(element.x, element.y).getState() != 0  && ((int) element.x != x || (int) element.y != y)) {
                        element.x -= element.vx/10;
                        element.vx = 0;
                    }

                    if (element.vx == 0 && element.vy == 0)
                        break;
                }
                game.setElement(element.x, element.y, element);
                if ((int) element.x != x || (int) element.y != y) {
                    element.timeSinceLastMove = 0;
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else {
                    element.timeSinceLastMove++;
                }
            }
            else {
                if (game.checkInBounds(x, y-1) && game.grid[x][y-1].element.getState() == 0 && Math.random() < 0.8) {
                    this.element.hasUpdated = true;
                    game.setElement(x, y-1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                    return;
                }
                else if (game.checkInBounds(x+1, y-1) && game.grid[x+1][y-1].element.getState() == 0 && Math.random() < 0.5) {
                    this.element.hasUpdated = true;
                    game.setElement(x+1, y-1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x-1, y-1) && game.grid[x-1][y-1].element.getState() == 0 && Math.random() < 0.5) {
                    this.element.hasUpdated = true;
                    game.setElement(x-1, y-1, this.element);
                    game.setElement(x, y, Element.Type.MPTY);
                }
                else if (game.checkInBounds(x, y-1) && (game.grid[x][y-1].element.getState() == 3 || game.grid[x][y-1].element.getState() == 4) && Math.random() < 0.6) {
                    this.element.hasUpdated = true;
                    Element tempElement = game.grid[x][y-1].element;
                    game.setElement(x, y-1, this.element);
                    game.setElement(x, y, tempElement);
                }
                else if (game.checkInBounds(x+1, y-1) && (game.grid[x+1][y-1].element.getState() == 3 || game.grid[x+1][y-1].element.getState() == 4) && Math.random() < 0.3) {
                    this.element.hasUpdated = true;
                    Element tempElement = game.grid[x+1][y-1].element;
                    game.setElement(x+1, y-1, this.element);
                    game.setElement(x, y, tempElement);
                }
                else if (game.checkInBounds(x-1, y-1) && (game.grid[x-1][y-1].element.getState() == 3 || game.grid[x-1][y-1].element.getState() == 4) && Math.random() < 0.3) {
                    this.element.hasUpdated = true;
                    Element tempElement = game.grid[x-1][y-1].element;
                    game.setElement(x-1, y-1, this.element);
                    game.setElement(x, y, tempElement);
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
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                        game.setElement(x, y, Element.Type.MPTY);
                        return;
                    }
                    
                    //Trying to flow left
                    while (xOffset >= -totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                        game.setElement(x, y, Element.Type.MPTY);
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
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                        game.setElement(x, y, Element.Type.MPTY);
                        return;
                    }
                    
                    //Trying to flow right
                    while (xOffset <= totalDistance) {
                        if (game.checkInBounds(x+xOffset, y) && game.grid[x+xOffset][y].element.getState() == 0) {
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
                        game.setElement(x, y, Element.Type.MPTY);
                        return;
                    }
                }
            }
            }
            return;
        }
    }
}
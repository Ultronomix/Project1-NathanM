package ultranomics.enterprisefoundationsproject.DTOs;

public class ResourceCreationDTO {
    private String resourceID;
    
    //make Jackson happy with default constructor
    public ResourceCreationDTO(){
        super();
    }
    
    public ResourceCreationDTO(String importID){
        this.resourceID = importID;
    }
    
    public String getResourceId() {
        return resourceID;
    }

    public void setResourceId(String resourceId) {
        this.resourceID = resourceId;
    }
    
    
    //concerned the formatting on toString methods needs to be something 
    //specific that I don't fully understand to interact with JSON
    @Override
    public String toString(){
        return "ResourceCreationDTO{" + 
                "resourceID= " + resourceID + 
                '}';
    }
}

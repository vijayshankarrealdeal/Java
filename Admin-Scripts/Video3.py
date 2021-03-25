import firebase_admin
from firebase_admin import credentials,firestore
from firebase_admin import storage
cred = credentials.Certificate("./adminKey.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'women-e598c.appspot.com'
})
#Database Methods
db = firestore.client()

#discrip = ""


title = "Pursue Passions"
cloudStorageLink = "https://firebasestorage.googleapis.com/v0/b/women-e598c.appspot.com/o/y2mate.com%20-%20Melinda%20Gates%20Pursue%20Passions%20with%20a%20Vengeance%20Entire%20Talk_v240P.mp4?alt=media&token=449aca02-7f21-4cbd-a1d1-00ffc1a3b3e0"
name = "Stanford eCorner"
source = "YouTube"
sourceLink = "https://www.youtube.com/watch?v=Umy7UpwcRpc"
discription = "Through stories of creating meaningful impact around the world, Melinda Gates explains how the Gates Foundation seeks to solve global challenges through innovation in the areas of health and education. Gates also shares life experiences and insights gained on working with others, pursuing life passions, and being committed to volunteerism."
viewsOnVideo = 453,120,46176
socialHandle = " https://read.bi/2xCnzGF"
webpage = "https://www.businessinsider.com"

if(len(title)!=0 and len(cloudStorageLink)!=0):
    videsoWrite =  db.collection("adminContent").document("Videos").collection("data").document().set({
        "title":title,
        "name":name,
        "source":source,
        "sourceLink":sourceLink,
        "discription":discription,
        "viewsOnVideo":viewsOnVideo,
        "socialHandle":socialHandle,
        "webpage":webpage,
        "cloudStorageLink":cloudStorageLink
    }) 
else:
    print("Error")

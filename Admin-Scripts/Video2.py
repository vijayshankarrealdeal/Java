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


title = "GenderEquality"
cloudStorageLink = "https://firebasestorage.googleapis.com/v0/b/women-e598c.appspot.com/o/y2mate.com%20-%20Melinda%20Gates%20On%20Marriage%20Gender%20Equality%20%20Solving%20Tough%20Problems.mp4?alt=media&token=82126c96-8141-4634-97ae-85a4725913b5"
name = "Business Insider"
source = "YouTube"
sourceLink = "https://www.youtube.com/watch?v=BuYfALzDPrY"
discription = "Gender equality is key. That means having balanced relationships where both partners split the workload at home. This is something that even Melinda and Bill have had to work at. Gates details all these findings in her new book, The Moment of Lift."
viewsOnVideo = 648,568,59947
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
